package com.web.curation.controller.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.model.user.User;
import com.web.curation.security.PasswordEncoding;
import com.web.curation.service.IProfileService;
import com.web.curation.service.IUserService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin("*")
@RestController
public class MailController {
	
	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IUserService userService;
	
	@Autowired
	private IProfileService profileService;
	
	@PostMapping("/mail/resend")
	@ApiOperation(value = "회원가입 이메일 재 전송")	
	public String reSendMailForJoin(@RequestParam(required = true) String email) throws Exception {
		log.info("POST : /mail/resend");
		
		String keyCode = MailUtil.createKey();
		User user = new User(email, keyCode, 0);
		if (userService.updateConfirm(user) != 1) {
			return "failed";
		}

		String subject = "푸드득(得) 회원가입 인증 메일입니다.";

		StringBuilder msg = new StringBuilder();
		msg.append("<div align='center' style='border:1px solid black; font-family:verdana'>");
		msg.append("<h2> 푸드득(得) 회원가입 인증 메일입니다. </h2>");
		msg.append("<div style='font-size: 100%'>");
		msg.append("<h3>환영합니다! ");
		msg.append("<strong> " + keyCode + "</strong> 를 입력해주세요.</h2></div></div>");
		try {
			MailUtil.sendMail(email, subject, msg.toString());
			return "success";
		} catch (Exception e) {
			log.error(e+"");
			return "failed";
		}
		
	}
	
	@PatchMapping("/mail/confirm")
	@ApiOperation(value = "회원가입 인증")
	public String updateConfirm(@RequestParam(required = true) String email,
								@RequestParam(required = true) String key) throws Exception {
		log.info("PATCH : /mail/confirm");
		
		if(email == null || key == null) {
			return "failed";
		}
		
		String keyCode = userService.getConfirm(email);
		
		if(!key.equals(keyCode)) {
			return "failed";
		}
		
		if(userService.successJoin(email) != 1) {
			return "failed";
		}
		
		int num = userService.getNumByEmail(email);
		profileService.createProfile(num);
		
		return "success";
	}
	
	@PostMapping("/mail/certification")
	@ApiOperation(value = "비밀번호 변경 본인 인증 메일 발송")	
	public String checkEmail(@RequestParam(required = true) String email) throws Exception { 
		log.info("POST : /mail/certification");
		
		if(email == null) {
			return "failed";
		}

		String newKey = MailUtil.createKey();
		User user = new User(email, newKey, 0, 0);
		if(userService.updateKey(user) != 1) {
			return "failed";
		}
		
		String subject = "푸드득(得) 비밀번호 찾기 본인 인증 메일입니다.";
		
		StringBuilder msg = new StringBuilder();
		msg.append("<div align='center' style='border:1px solid black; font-family:verdana'>");
		msg.append("<h2> 푸드득(得) 비밀번호 찾기 본인 인증 메일입니다. </h2>");
		msg.append("<div style='font-size: 100%'>");
		msg.append("<strong> " + newKey + "</strong> 를 입력해주세요.</h2></div></div>");
		
		MailUtil.sendMail(email, subject, msg.toString());
		
		return "success";
	}
	
	@PostMapping("/mail/key")
	@ApiOperation(value = "본인 인증 키 인증")	
	public String compareKey(@RequestParam(required = true) String email,
							@RequestParam(required = true) String key) throws Exception {
		log.info("POST : /mail/key");
		
		String getKey = userService.getKey(email);
		
		if(!key.equals(getKey)) {
			return "failed";
		}
		
		return "success";
	}
	
	@PostMapping("/mail/password")
	@ApiOperation(value = "비밀번호 변경 임시 비밀번호 발송")	
	public String sendMailFindPassword(@RequestParam(required = true) String email) throws Exception {
		log.info("POST : /mail/password");
		
		if(email == null) {
			return "failed";
		}
		PasswordEncoding passwordEncoding = new PasswordEncoding();
		String newPwd = MailUtil.getNewPwd();
		String encodePwd = passwordEncoding.encode(newPwd);
		User user = new User(email, encodePwd);
		if(userService.updatePassword(user) != 1) {
			return "failed";
		}
		
		String subject = "푸드득(得) 임시 비밀번호 발급 메일입니다.";
		
		StringBuilder msg = new StringBuilder();
		msg.append("<div align='center' style='border:1px solid black; font-family:verdana'>");
		msg.append("<h2> 푸드득(得) 임시 비밀번호 발급 메일입니다. </h2>");
		msg.append("<p>로그인 후 비밀번호를 반드시 변경하세요</p>");
		msg.append("<div style='font-size: 100%'>");
		msg.append("<p>임시 비밀번호 : <strong> " + newPwd + "</strong> 를 입력해주세요.</p></div></div>");
		
		MailUtil.sendMail(email, subject, msg.toString());
		
		return "success";
	}
	
	public String getSocial(String email) {
		log.info("METHOD : getSocial");
		
		return userService.getSocial(email);
	}
	
}
