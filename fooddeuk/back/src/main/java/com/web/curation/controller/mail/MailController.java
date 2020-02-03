package com.web.curation.controller.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.model.user.User;
import com.web.curation.service.IProfileService;
import com.web.curation.service.IUserService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin("*")
@RestController
public class MailController {
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IProfileService profileService;
	
	@PostMapping("/mail/reSendMailForJoin")
	@ApiOperation(value = "회원가입 이메일 재전송")	
	public String reSendMailForJoin(@RequestParam(required = true) String email) throws Exception {
		System.out.println("-----------------reSendMailForJoin-----------------");
		System.out.println("email : " + email);
		
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
			System.out.println(e);
			return "failed";
		}
		
	}
	
	@PostMapping("/mail/updateConfirm")
	@ApiOperation(value = "회원가입 인증")
	public String updateConfirm(@RequestParam(required = true) String email,
								@RequestParam(required = true) String key) throws Exception {
		System.out.println("-----------------updateConfirm-----------------");
		System.out.println("email : " + email);
		System.out.println("key : " + key);
		
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
	
	@PostMapping("/mail/checkEmail")
	@ApiOperation(value = "비밀번호 변경 본인 인증 메일 발송")	
	public String checkEmail(@RequestParam(required = true) String email) throws Exception { 
		System.out.println("-----------------checkEmail-----------------");
		System.out.println("email : " + email);
		
		if(email == null) {
			return "failed";
		}

		String newKey = MailUtil.createKey();
		User user = new User(email, newKey, 0, 0);
		System.out.println(user.toString());
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
	
	@PostMapping("mail/sendMailTempPassword")
	@ApiOperation(value = "비밀번호 변경 임시 비밀번호 발송")	
	public String sendMailFindPassword(@RequestParam(required = true) String email) throws Exception {
		System.out.println("-----------------sendMailFindPassword-----------------");
		System.out.println("email : " + email);
		
		if(email == null) {
			return "failed";
		}
		
		String newPwd = MailUtil.getNewPwd();
		if(userService.updatePassword(email) != 1) {
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
		return userService.getSocial(email);
	}
	
}
