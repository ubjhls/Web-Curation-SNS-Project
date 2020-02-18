package com.web.curation.controller.mail;

import java.util.Random;

import org.apache.commons.mail.HtmlEmail;
import org.springframework.web.bind.annotation.CrossOrigin;

public class MailUtil {
	
	public static void sendMail(String email, String subject, String msg) throws Exception {
		
		// Mail Server 설정
		String charSet = "utf-8";
		String hostSMTP = "smtp.naver.com";
		String hostSMTPid = "lu_nen02";
		String hostSMTPpwd = "temp1q2w3e4r!!";
		
		// 보내는 사람
		String fromEmail = "lu_nen02@naver.com"; 
		String fromName = "푸드득";
		
		HtmlEmail mail = new HtmlEmail();
		mail.setDebug(true);
		mail.setCharset(charSet);
		mail.setSSLOnConnect(true); // SSL 사용(TLS가 없는 경우 SSL 사용)
		mail.setHostName(hostSMTP);
		mail.setSmtpPort(587); // SMTP 포트 번호
		
		mail.setAuthentication(hostSMTPid, hostSMTPpwd);
		mail.setStartTLSEnabled(true);
		mail.addTo(email);
		mail.setFrom(fromEmail, fromName, charSet);
		mail.setSubject(subject);
		mail.setHtmlMsg(msg);
		mail.send();
	}
	
	// 임시번호 랜덤 생성
	public static String createKey() throws Exception{

		StringBuilder key = new StringBuilder();
		Random rnd = new Random();
		
		for(int i = 0; i < 10 ; i++) {
			int index = rnd.nextInt(3);
			
			switch(index) {
			case 0 :
				key.append((char) ((int) (rnd.nextInt(26)) + 97));
				break;
			case 1:
				key.append((char) ((int) (rnd.nextInt(26)) + 65));
				break;
			case 2:
				key.append((rnd.nextInt(10)));
				break;
			}
		}
		
		return key.toString();
	}
	
	public static String getNewPwd() throws Exception {
		char[] charSet = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9' ,
				'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 
				'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
				'W', 'X', 'Y', 'Z',
				'!', '@', '#', '$', '%', '^', '&', '+', '=', '.'};
		
		StringBuilder newKey;
		
		while(true) {
			newKey = new StringBuilder();
			
			int alpha = 0, number = 0;
			for(int i = 0; i < 10; i++) {
				int idx = (int) (charSet.length * Math.random());;
				if(idx >= 0 && idx <= 9) { // 숫자
					number++;
				} else if(idx >= 10 && idx <= 35) { // 알파벳
					alpha++;
				}
				newKey.append(charSet[idx]);
			}
			if(alpha >= 0 && number >= 0) {
				break;
			}
		}
		
		return newKey.toString();
	}
	
}