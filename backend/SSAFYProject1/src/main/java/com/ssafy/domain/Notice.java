package com.ssafy.domain;


public class Notice {

	private int notice_no;
	private String notice_id;
	private String notice_title;
	private String notice_content;
	private String notice_regtime;
	public int getNotice_no() {
		return notice_no;
	}
	public void setNotice_no(int notice_no) {
		this.notice_no = notice_no;
	}
	public String getNotice_id() {
		return notice_id;
	}
	public void setNotice_id(String notice_id) {
		this.notice_id = notice_id;
	}
	public String getNotice_title() {
		return notice_title;
	}
	public void setNotice_title(String notice_title) {
		this.notice_title = notice_title;
	}
	public String getNotice_content() {
		return notice_content;
	}
	public void setNotice_content(String notice_content) {
		this.notice_content = notice_content;
	}
	public String getNotice_regtime() {
		return notice_regtime;
	}
	public void setNotice_regtime(String notice_regtime) {
		this.notice_regtime = notice_regtime;
	}
	@Override
	public String toString() {
		return "Notice [notice_no=" + notice_no + ", notice_id=" + notice_id + ", notice_title=" + notice_title
				+ ", notice_content=" + notice_content + ", notice_regtime=" + notice_regtime + "]";
	}

	
}
