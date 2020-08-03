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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.domain.Violation;
import com.ssafy.service.UserService;
import com.ssafy.service.ViolationService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("api/violation")
public class ViolationController {
	private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	UserService userService;
	@Autowired
	ViolationService violationService;

	@ApiOperation(value = "모든 제보 정보를 리스트로 반환한다.", response = List.class)
	@GetMapping("{userNo}")
	public ResponseEntity<List<Violation>> viewViolaions(@PathVariable int userNo) throws Exception {
		logger.debug("신고 리스트 조회 - 호출");
		// 이메일로 회원번호 조회
		return new ResponseEntity<List<Violation>>(violationService.viewViolationList(userNo), HttpStatus.OK);
	}

	@ApiOperation(value = "모든 제보 정보를 리스트로 반환한다.", response = Violation.class)
	@GetMapping("{userNo}/{violationNo}")
	public ResponseEntity<Violation> viewViolation(@PathVariable int userNo, @PathVariable int violationNo) throws Exception {
		logger.debug("신고  조회 - 호출");
		Violation abc = violationService.viewViolation(violationNo, userNo);
		System.out.println(abc.toString());
		return new ResponseEntity<Violation>(violationService.viewViolation(violationNo, userNo), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<String> addViolation(@RequestBody Violation violation) throws Exception {
		logger.debug("신고 정보 삽입 - 호출");
		
		if(violationService.addViolation(violation) == 1) return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		return new ResponseEntity<String>(FAIL, HttpStatus.NOT_ACCEPTABLE);
	}

	@PutMapping
	public ResponseEntity<String> modifyViolation(@RequestBody Violation violation) throws Exception {
		logger.debug("신고 정보 수정 - 호출");
		
		if(violationService.modifyViolation(violation) == 1) return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		return new ResponseEntity<String>(FAIL, HttpStatus.NOT_ACCEPTABLE);
	}

	@DeleteMapping("{userNo}/{violationNo}")
	public ResponseEntity<String> removeViolation(@PathVariable int violationNo, @PathVariable int userNo) throws Exception {
		logger.debug("신고 정보 삭제 - 호출");
		
		if(violationService.removeViolation(violationNo, userNo) == 1) return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		return new ResponseEntity<String>(FAIL, HttpStatus.NOT_ACCEPTABLE);
	}
}