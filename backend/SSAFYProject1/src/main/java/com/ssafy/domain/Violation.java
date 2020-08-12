package com.ssafy.domain;

public class Violation {
	private int violationNo;
	private int userNo;
	private String email;
	private String item;
	private String date;
	private String time;
	private String carNum;
	private String spot;
	private String address;
	private String contents;
	private String videoUrl;
	private int reportStatus;
	private String lat;
	private String lng;

	public Violation() {}
	public Violation(int violationNo, int userNo, String email, String item, String date, String time, String carNum,
			String spot, String address, String contents, String videoUrl, int reportStatus, String lat, String lng) {
		this.violationNo = violationNo;
		this.userNo = userNo;
		this.email = email;
		this.item = item;
		this.date = date;
		this.time = time;
		this.carNum = carNum;
		this.spot = spot;
		this.address = address;
		this.contents = contents;
		this.videoUrl = videoUrl;
		this.reportStatus = reportStatus;
		this.lat = lat;
		this.lng = lng;
	}

	public int getViolationNo() {
		return violationNo;
	}

	public void setViolationNo(int violationNo) {
		this.violationNo = violationNo;
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

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getCarNum() {
		return carNum;
	}

	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}

	public String getSpot() {
		return spot;
	}

	public void setSpot(String spot) {
		this.spot = spot;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public int getReportStatus() {
		return reportStatus;
	}

	public void setReportStatus(int reportStatus) {
		this.reportStatus = reportStatus;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}
	
	@Override
	public String toString() {
		return "Violation [violationNo=" + violationNo + ", userNo=" + userNo + ", email=" + email + ", item=" + item
				+ ", date=" + date + ", time=" + time + ", carNum=" + carNum + ", spot=" + spot + ", address=" + address
				+ ", contents=" + contents + ", videoUrl=" + videoUrl + ", reportStatus=" + reportStatus + ", lat="
				+ lat + ", lng=" + lng + "]";
	}

}
