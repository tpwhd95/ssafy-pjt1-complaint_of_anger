package com.ssafy.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.domain.Notice;
import com.ssafy.service.NoticeService;
import com.ssafy.util.JWTUtil;

import io.swagger.annotations.ApiOperation;

//http://localhost:9999/happyhouse/swagger-ui.html
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/api/notice")
public class NoticeController {

	private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	private static final String WRONG = "wrong";

	@Autowired
	private NoticeService noticeService;

    @ApiOperation(value = "모든 공지사항의 정보를 반환한다.", response = List.class)
	@GetMapping
	public ResponseEntity<List<Notice>> retrieveNotice() throws Exception {
		logger.debug("retrieveNotice - 호출");
		return new ResponseEntity<List<Notice>>(noticeService.retrieveNotice(), HttpStatus.OK);
	}
    
    
    @ApiOperation(value = "새로운 공지사항을 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping
	public ResponseEntity<String> writeNotice(@RequestBody Notice notice, @RequestHeader("token") String token) {
		logger.debug("writeNotice - 호출");
		System.out.println(notice);
		
		new JWTUtil();
		if(JWTUtil.verifyToken(token).equals("관리자")) System.out.println("토큰 검증 완료!!");
		else {
			return new ResponseEntity<String>(WRONG, HttpStatus.BAD_REQUEST);
		}
		
		if (noticeService.writeNotice(notice)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
    @ApiOperation(value = "글번호에 해당하는 공지사항의 정보를 반환한다.", response = Notice.class)    
	@GetMapping("{notice_no}")
	public ResponseEntity<Notice> detailNotice(@PathVariable int notice_no) {
		logger.debug("detailNotice - 호출");
		return new ResponseEntity<Notice>(noticeService.detailNotice(notice_no), HttpStatus.OK);
	}
    
    
    @ApiOperation(value = "글번호에 해당하는 공지사항의 정보를 수정한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("{notice_no}")
	public ResponseEntity<String> updateNotice(@RequestBody Notice notice, @RequestHeader("token") String token) {
		logger.debug("updateNotice - 호출");
		
		new JWTUtil();
		if(JWTUtil.verifyToken(token).equals("관리자")) System.out.println("토큰 검증 완료!!");
		else {
			return new ResponseEntity<String>(WRONG, HttpStatus.BAD_REQUEST);
		}
		
		if (noticeService.updateNotice(notice)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
    
    @ApiOperation(value = "글번호에 해당하는 공지사항의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("{notice_no}")
	public ResponseEntity<String> deleteNotice(@PathVariable int notice_no, @RequestHeader("token") String token) {
		logger.debug("deleteNotice - 호출");
		System.out.println(token);
		
		new JWTUtil();
		if(JWTUtil.verifyToken(token).equals("관리자")) System.out.println("토큰 검증 완료!!");
		else return new ResponseEntity<String>(WRONG, HttpStatus.BAD_REQUEST);
		
		if (noticeService.deleteNotice(notice_no)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

}