package com.ssafy.service;

import com.ssafy.domain.User;

public interface UserService {
	User login(User user) throws Exception;

	User detail(String email) throws Exception;
	
	User detailByNo(int userNo) throws Exception;

	int signUp(User user) throws Exception;

	int modify(User user) throws Exception;

	int remove(String email) throws Exception; // 리스트로 회원을 한꺼번에 remove 할지 고민
	
	String findEmail(User user) throws Exception;
	
	User findPassword(User user) throws Exception;
}
