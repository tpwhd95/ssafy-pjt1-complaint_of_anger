package com.ssafy.service;

import com.ssafy.domain.User;

public interface SNSUserService {
	User login(User user) throws Exception;

	User detail(String email) throws Exception;

	int signUp(User user) throws Exception;

	int modify(User user) throws Exception;

	int remove(String email) throws Exception; // 리스트로 회원을 한꺼번에 remove 할지 고민
}
