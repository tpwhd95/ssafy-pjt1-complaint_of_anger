package com.ssafy.domain;

public class User {
	private int UserNo;
	private String email;
	private String password;
	private String name;
	private String gender;
	private String birth;
	private String phone;

	public User() {}
	public User(int userNo, String email, String password, String name, String gender, String birth, String phone) {
		this.UserNo = userNo;
		this.email = email;
		this.password = password;
		this.name = name;
		this.gender = gender;
		this.birth = birth;
		this.phone = phone;
	}

	public int getUserNo() {
		return UserNo;
	}

	public void setUserNo(int userNo) {
		UserNo = userNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Override
	public String toString() {
		return "User [UserNo=" + UserNo + ", email=" + email + ", password=" + password + ", name=" + name + ", gender="
				+ gender + ", birth=" + birth + ", phone=" + phone + "]";
	}
}
