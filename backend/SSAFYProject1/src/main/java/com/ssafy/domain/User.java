package com.ssafy.domain;

public class User {
	private int userNo;
	private String email;
	private String password;
	private String name;
	private String gender;
	private String birth;
	private String phone;
	private boolean isSNS;

	public User() {}
	public User(int userNo, String email, String password, String name, String gender, String birth, String phone,
			boolean isSNS) {
		this.userNo = userNo;
		this.email = email;
		this.password = password;
		this.name = name;
		this.gender = gender;
		this.birth = birth;
		this.phone = phone;
		this.isSNS = isSNS;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
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
	
	public boolean isSNS() {
		return isSNS;
	}

	public void setSNS(boolean isSNS) {
		this.isSNS = isSNS;
	}
	
	@Override
	public String toString() {
		return "User [userNo=" + userNo + ", email=" + email + ", password=" + password + ", name=" + name + ", gender="
				+ gender + ", birth=" + birth + ", phone=" + phone + ", isSNS=" + isSNS + "]";
	}
}
