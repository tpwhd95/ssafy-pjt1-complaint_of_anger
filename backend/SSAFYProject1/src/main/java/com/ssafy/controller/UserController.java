package com.ssafy.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.domain.User;
import com.ssafy.service.UserService;

import io.swagger.annotations.ApiOperation;

//http://localhost:9999/happyhouse/swagger-ui.html
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("api/user")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	private static final String ERROR = "error";

	@Autowired
	UserService service;
	
	@ApiOperation(value = "로그인 후 성공 여부를 반환한다.")
	@PostMapping("login") // 로그인
	public ResponseEntity<String> login(@RequestBody Map<String, Object> param, HttpSession session) {
		logger.debug("login - 호출");
		System.out.println(param);
		
		User input = new User();
		input.setEmail(param.get("email").toString());
		input.setPassword(param.get("password").toString());

		try {
			User user = service.login(input); // 회원 조회
			
			if(user != null) { // 회원 존재
				session.setAttribute("userinfo", user);
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			} else return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(ERROR, HttpStatus.NOT_ACCEPTABLE);
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
	public ResponseEntity<Map<String, Object>> userInfo(String email, Model model) {
		logger.debug("회원조회 - 호출");
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			User user = service.detail(email);
			
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
			if(service.detail(user.getEmail()) == null) return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
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
	public ResponseEntity<String> update(@RequestBody User user) {
		logger.debug("회원수정 - 호출");
		System.out.println(user);
		
		try {
			if(service.modify(user) == 1) return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			else return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(ERROR, HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	@ApiOperation(value = "회원삭제 후 성공 여부를 반환한다.")
	@PostMapping("del") // 회원 삭제
	public ResponseEntity<String> delete(@RequestBody Map<String, Object> param) {
		logger.debug("회원삭제 - 호출");
		System.out.println(param);
		String email = param.get("email").toString();
		
		try {
			if(service.remove(email) == 1) return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			else return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(ERROR, HttpStatus.NOT_ACCEPTABLE);
		}
	}
}
