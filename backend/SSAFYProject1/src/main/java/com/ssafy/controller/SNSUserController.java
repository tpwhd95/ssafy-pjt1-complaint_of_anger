package com.ssafy.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssafy.domain.User;
import com.ssafy.service.UserService;
import com.ssafy.util.JWTUtil;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@Controller
@RequestMapping("/api/sns")
public class SNSUserController {
	private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);
	private static final String SUCCESS = "success";
	
	static final String clientId = "px2gRec1H_fbAwS22rLW"; // 애플리케이션 클라이언트 아이디값";
	static final String naverClientSecret = "iQmYgJMpWR"; // 애플리케이션 클라이언트 시크릿값
	static String access_token; // 접근 토큰
	static String refresh_token; // 갱신 토큰

	@Autowired
	UserService service;

	/*
	 * 네이버 아이디로 로그인 네이버 아이디로 로그인 인증 요청 => Front
	 */
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String naverCallback(@RequestParam(value = "code") String code, @RequestParam(value = "state") String state)
			throws Exception {
		logger.debug("네이버 로그인 - 호출");
		User user = new User();
		String JWT_token = null;

		String naverLoginURL; // 접근 토큰 발급 요청 URL
		naverLoginURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&";
		naverLoginURL += "client_id=" + clientId;
		naverLoginURL += "&client_secret=" + naverClientSecret;
		naverLoginURL += "&code=" + code;
		naverLoginURL += "&state=" + state;
		System.out.println("네이버 아이디 로그인 URL >> " + naverLoginURL);

		try { // 접근 토큰 발급 요청
			URL url = new URL(naverLoginURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			BufferedReader br;

			if (responseCode == 200) br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			else br = new BufferedReader(new InputStreamReader(con.getErrorStream()));

			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			br.close();
			
			if (responseCode == 200) { // 성공적으로 토큰을 가져온다면
				// JSON In Java API
				JSONTokener tokener = new JSONTokener(response.toString());
				JSONObject accessObject = new JSONObject(tokener);
				access_token = accessObject.getString("access_token");
				System.out.println("access_token >> " + access_token);

				tokener = new JSONTokener(getUserInfo(access_token).toString());
				JSONObject userInfoObject = new JSONObject(tokener).getJSONObject("response");
				System.out.println(userInfoObject);

				int id = userInfoObject.getInt("id");
				String email = userInfoObject.getString("email");
				String name = userInfoObject.getString("name");
				String birth = userInfoObject.getString("birthday");
				String gender;
				if(userInfoObject.getString("gender").equals("M")) gender = "남";
				else gender = "여";
				
				user.setEmail(email); user.setName(name); user.setBirth(birth); user.setGender(gender); user.setSNS(true);
				
				// User Table에 데이터 저장
				try {
					logger.debug("네이버 회원 등록 - 호출");
					if(service.detail(user.getEmail().toString()) == null) {
						if(service.signUp(user) == 1) System.out.println("DB Insert Success!!");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				int userNo = service.detail(email).getUserNo();
				
				new JWTUtil();
				JWT_token = JWTUtil.createJWTToken(name, email, birth, gender, userNo, true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:http://localhost:8081?token=" + JWT_token;
	}

	/*
	 * 네이버 프로필 가져오기 요청 헤더 예) Authorization: Bearer + access_token
	 */
	private String getUserInfo(String access_token) {
		String header = "Bearer " + access_token; // Bearer 다음에 공백 추가(네이버 요구 형식)
		try {
			String apiURL = "https://openapi.naver.com/v1/nid/me";

			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Authorization", header);
			int responseCode = con.getResponseCode();
			BufferedReader br;

			if (responseCode == 200) br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			else br = new BufferedReader(new InputStreamReader(con.getErrorStream()));

			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			br.close();

			return response.toString();
		} catch (Exception e) {
			System.err.println(e);
			return "Error";
		}
	}

	/*
	 * 네이버 로그아웃 접근 토큰 삭제
	 */
	@ApiOperation(value = "네이버 로그아웃 후 성공 여부를 반환한다.")
	@GetMapping("/logout")
	public @ResponseBody ResponseEntity<String> logout() throws Exception {
		logger.debug("네이버 로그아웃 - 호출");

		// 접근 토큰 삭제 URL
		String apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=delete&client_id=";
		apiURL += clientId + "&client_secret=";
		apiURL += naverClientSecret + "&access_token=";
		apiURL += access_token + "&service_provider=NAVER";

		URL url = new URL(apiURL);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		int responseCode = con.getResponseCode();
		BufferedReader br;

		if (responseCode == 200) { // 정상 호출
			System.out.println("Access Token Delete Success");
			access_token = null;
			br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		} else br = new BufferedReader(new InputStreamReader(con.getErrorStream()));

		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = br.readLine()) != null) {
			response.append(inputLine);
		}
		br.close();

		System.out.println(response.toString());
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}
}
