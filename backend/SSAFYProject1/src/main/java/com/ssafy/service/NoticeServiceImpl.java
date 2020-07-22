package com.ssafy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.repository.NoticeDAO;
import com.ssafy.domain.Notice;



@Service
public class NoticeServiceImpl implements NoticeService {
	
    @Autowired
	private NoticeDAO noticeDao;

    @Override
	public List<Notice> retrieveNotice() {
		return noticeDao.selectNotice();
	}
    
  	@Override
	public boolean writeNotice(Notice notice) {
		return noticeDao.insertNotice(notice) == 1;
	}

	@Override
	public Notice detailNotice(int notice_no) {
		return noticeDao.selectNoticeBynotice_no(notice_no);
	}

	@Override
	@Transactional
	public boolean updateNotice(Notice notice) {
		return noticeDao.updateNotice(notice) == 1;
	}

	@Override
	@Transactional
	public boolean deleteNotice(int notice_no) {
		return noticeDao.deleteNotice(notice_no) == 1;
	}
}