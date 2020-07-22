package com.ssafy.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.domain.User;
import com.ssafy.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	UserService service;
	
	@PostMapping("login") // 로그인
	public String login(String email, String password, HttpSession session) {
		System.out.println("로그인 시도");
		String result = "";
		
		User input = new User();
		input.setEmail(email);
		input.setPassword(password);
		
		try {
			User user = service.login(input);
			if(user != null) {
				session.setAttribute("userinfo", user);
				result = "success";
			} else result = "null";
		} catch (Exception e) {
			e.printStackTrace();
			result = "error";
		}
		System.out.println(result);
		return result;
	}
	
	@GetMapping("logout") // 로그아웃
	public String logout(HttpSession session) {
		System.out.println("로그아웃 시도");
		session.invalidate();
		return "success";
	}
	
	@GetMapping // 회원 조회
	public String userInfo(String email, Model model) {
		System.out.println("회원 조회 시도");
		String result = "";
		
		try {
			User user = service.detail(email);
			result = "success"; // 회원 조회는 테이블에서 눌러서 조회 -> User == null 일 경우가 없ㅇ므
			
			model.addAttribute("userinfo", user);
		} catch (Exception e) {
			e.printStackTrace();
			result = "error";
		}
		
		return result;
	}
	
	@PostMapping // 회원 등록
	public String signUp(User user, Model model) {
		System.out.println("회원 등록 시도");
		String result = "";
		
		try {
			int number = service.signUp(user);
			if(number == 1) result = "success";
			else result = "fail";
		} catch (Exception e) {
			e.printStackTrace();
			result = "error";
		}
		
		return result;
	}
	
	@PutMapping // 회원 수정
	public String update(User user) {
		System.out.println("회원 수정 시도");
		String result = "";
		
		try {
			if(service.modify(user) == 1) result = "success";
			else result = "fail";
		} catch (Exception e) {
			e.printStackTrace();
			result = "error";
		}
		return result;
	}
	
	@DeleteMapping // 회원 삭제
	public String delete(String email) {
		System.out.println("회원 삭제 시도");
		String result = "";
		
		try {
			if(service.remove(email) == 1) result = "success";
			else result = "fail";
		} catch (Exception e) {
			e.printStackTrace();
			result = "error";
		}
		
		return result;
	}
}
