package com.ssafy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.domain.User;
import com.ssafy.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository repo;

	@Override
	public User login(User user) throws Exception {
		return repo.select(user);
	}

	@Override
	public User detail(String email) throws Exception {
		return repo.selectUser(email);
	}

	@Override
	public int signUp(User user) throws Exception {
		return repo.insert(user);
	}

	@Override
	public int modify(User user) throws Exception {
		return repo.update(user);
	}

	@Override
	public int remove(String email) throws Exception {
		return repo.delete(email);
	}

	@Override
	public String findEmail(User user) throws Exception {
		return repo.findEmail(user);
	}

	@Override
	public User findPassword(User user) throws Exception {
		return repo.findPassword(user);
	}

	@Override
	public User detailByNo(int userNo) throws Exception {
		return repo.selectUserByNo(userNo);
	}
	
}
