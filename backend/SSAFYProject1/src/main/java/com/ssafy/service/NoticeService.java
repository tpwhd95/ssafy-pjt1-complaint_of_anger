package com.ssafy.service;

import java.util.List;

import com.ssafy.domain.Notice;


public interface NoticeService {
	public List<Notice> retrieveNotice();
	public Notice detailNotice(int notice_no);
	public boolean writeNotice(Notice notice);
	public boolean updateNotice(Notice notice);
	public boolean deleteNotice(int notice_no);
}
