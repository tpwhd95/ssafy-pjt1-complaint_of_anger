package com.ssafy.repository;

import org.springframework.stereotype.Repository;

import com.ssafy.domain.User;

@Repository
public interface UserRepository {
	User select(User user) throws Exception;

	User selectUser(String email) throws Exception;

	User selectUserByNo(int userNo) throws Exception;

	int insert(User user) throws Exception;

	int update(User user) throws Exception;

	int delete(String email) throws Exception;

	String findEmail(User user) throws Exception;

	User findPassword(User user) throws Exception;
}
