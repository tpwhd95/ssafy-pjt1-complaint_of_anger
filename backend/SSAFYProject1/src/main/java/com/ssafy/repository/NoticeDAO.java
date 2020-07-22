package com.ssafy.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.domain.Notice;


@Mapper
public interface NoticeDAO {
	public List<Notice> selectNotice();
	public Notice selectNoticeBynotice_no(int notice_no);
	public int insertNotice(Notice notice);
	public int updateNotice(Notice notice);
	public int deleteNotice(int notice_no);
}