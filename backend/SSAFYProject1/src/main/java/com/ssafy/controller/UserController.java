package com.ssafy.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.domain.User;
import com.ssafy.service.UserService;
import com.ssafy.util.JWTUtil;

import io.swagger.annotations.ApiOperation;

//http://localhost:9999/happyhouse/swagger-ui.html
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("api/user")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	private static final String ERROR = "error";
	private static final String WRONG = "wrong";

	@Autowired
	UserService service;
	
	@ApiOperation(value = "로그인 후 성공 여부를 반환한다.")
	@PostMapping("login") // 로그인
	public @ResponseBody ResponseEntity<String> login(@RequestBody Map<String, Object> param, Map<String, Object> map) {
		logger.debug("login - 호출");
		System.out.println(param);
		
		String JWT_token = null;
		User input = new User();
		input.setEmail(param.get("email").toString());
		input.setPassword(param.get("password").toString());
		
		// Gson gson = new Gson();					// Gson
		JSONObject object = new JSONObject();	// JSON In Java
		
		try {
			User user = service.login(input); // 회원 조회
			
			if(user != null) { // 회원 존재
				// Gson
				// map.put("userinfo", user);
				// map.put("success", SUCCESS);
				
				// JSON In Java
				object.put("userinfo", new JSONObject(user));
				object.put("success", SUCCESS);
				
				System.out.println(object.toString());
				// System.out.println(gson.toJson(map));
				
				new JWTUtil();
				JWT_token = JWTUtil.createJWTToken(user.getName(), user.getEmail(), user.getBirth(), user.getGender(), user.getUserNo(), false);
				object.put("token", JWT_token);
				System.out.println(JWT_token);
				
				return new ResponseEntity<String>(object.toString(), HttpStatus.OK); // gson.toJson(map)
			} else {
				
				map.put("fail", FAIL);
				return new ResponseEntity<String>(object.toString(), HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("error", ERROR);
			return new ResponseEntity<String>(object.toString(), HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	@ApiOperation(value = "로그아웃 후 성공 여부를 반환한다.")
	@GetMapping("logout") // 로그아웃
	public ResponseEntity<String> logout(HttpSession session) {
		logger.debug("logout - 호출");
		session.invalidate();
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}
	
	@ApiOperation(value = "이메일에 맞는 회원 정보를 반환한다.", response = User.class)
	@GetMapping // 회원 조회
	public ResponseEntity<Map<String, Object>> userInfo(String email, @RequestHeader("token") String token) {
		logger.debug("회원조회 - 호출");
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			User user = service.detail(email);
			
			new JWTUtil();
			if (JWTUtil.verifyToken(token).equals("관리자")) System.out.println("토큰 검증 완료!!");
			else {
				map.put("wrong", WRONG);
				return new ResponseEntity<Map<String, Object>>(map, HttpStatus.BAD_REQUEST);
			}
			
			map.put("userinfo", user);
			map.put("success", SUCCESS);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("error", ERROR);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	@ApiOperation(value = "회원등록 후 성공 여부를 반환한다.")
	@PostMapping // 회원 등록
	public ResponseEntity<String> signUp(@RequestBody User user, Model model) {
		logger.debug("회원등록 - 호출");
		System.out.println(user);		
		
		try {
			if(service.detail(user.getEmail()) != null) return new ResponseEntity<String>(FAIL, HttpStatus.NOT_ACCEPTABLE);
			else {
				int number = service.signUp(user);
				if (number == 1) return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
				else return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(ERROR, HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	@ApiOperation(value = "회원수정 후 성공 여부를 반환한다.")
	@PutMapping // 회원 수정
	public ResponseEntity<Map<String, Object>> update(@RequestBody User user, @RequestHeader("token") String token) {
		logger.debug("회원수정 - 호출");
		System.out.println("유저 토큰 >> " + token);
		Map<String, Object> map = new HashMap<String, Object>();

		new JWTUtil();
		if(JWTUtil.verifyToken(token).equals(user.getName())) System.out.println("토큰 검증 완료!!");
		else {
			map.put("wrong", WRONG);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.BAD_REQUEST);
		}
		
		try {
			if (service.modify(user) == 1) {
				map.put("userinfo", service.detail(user.getEmail()));
				map.put("success", SUCCESS);
				return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
			} else {
				map.put("fail", FAIL);
				return new ResponseEntity<Map<String, Object>>(map, HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("error", ERROR);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	@ApiOperation(value = "회원삭제 후 성공 여부를 반환한다.")
	@PostMapping("del") // 회원 삭제
	public ResponseEntity<String> delete(@RequestBody Map<String, Object> param, @RequestHeader("token") String token) {
		logger.debug("회원삭제 - 호출");
		System.out.println(param);
		String email = param.get("email").toString();
		
		try {
			User user = service.detail(email);
			
			new JWTUtil();
			if(JWTUtil.verifyToken(token).equals(user.getName())) System.out.println("토큰 검증 완료!!");
			else return new ResponseEntity<String>(WRONG, HttpStatus.BAD_REQUEST);
			
			if(service.remove(email) == 1) return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			else return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(ERROR, HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	@ApiOperation(value = "이메일을 찾은 후 이메일을 반환한다.")
	@PostMapping("findEmail")
	public ResponseEntity<String> findEmail(@RequestBody User user) {
		logger.debug("이메일 찾기 - 호출");
		System.out.println(user);
		
		try {
			String email = service.findEmail(user);
			if(email != null) return new ResponseEntity<String>(email, HttpStatus.OK);
			else return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(ERROR, HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	@ApiOperation(value = "이메일을 찾은 후 랜덤 비밀번호를 반환한다.")
	@PostMapping("findPassword")
	public ResponseEntity<String> findPassword(@RequestBody User input) {
		logger.debug("비밀번호 찾기 - 호출");
		System.out.println(input);
		
		try {
			User user = service.findPassword(input);
			if(user != null) {
				String password = UUID.randomUUID().toString();
				System.out.println(password);
				password = password.substring(password.lastIndexOf("-") + 1);
				System.out.println(password);
				user.setPassword(password);
				
				if(service.modify(user) == 1) return new ResponseEntity<String>(password, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(ERROR, HttpStatus.NOT_ACCEPTABLE);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
}
