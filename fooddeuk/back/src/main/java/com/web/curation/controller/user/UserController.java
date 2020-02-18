package com.web.curation.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.model.search.Search;
import com.web.curation.model.user.User;
import com.web.curation.security.PasswordEncoding;
import com.web.curation.service.ISearchService;
import com.web.curation.service.IUserService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin("*")
@RestController
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private ISearchService searchService;
	
	@PatchMapping("/user/user")
	@ApiOperation(value = "회원 정보 수정")
	public String updateUser(@RequestParam(required = true) String email,
							@RequestParam(required = true) String nickname, 
							@RequestParam(required = true) String intro, 
							@RequestParam(required = true) int auth) throws Exception {
		System.out.println("-----------------/user/user-----------------");
		System.out.println("email : " + email);
		System.out.println("nickname : " + nickname);
		System.out.println("intro : " + intro);
		System.out.println("auth : " + auth);
		
		User user = new User(email, nickname, intro, auth);
		
		if(userService.updateUser(user) != 1) {
			return "failed";
		}
		
		return "success";
	}
	
	@PatchMapping("/user/password")
	@ApiOperation(value = "비밀번호 수정")
	public String updateNewPassword(@RequestParam(required = true) String email, 
								@RequestParam(required = true) String password) throws Exception {
		System.out.println("-----------------/user/password-----------------");
		System.out.println("email : " + email);
		System.out.println("password : " + password);
		
		
		PasswordEncoding passwordencoding = new PasswordEncoding();
		String encodePassword = passwordencoding.encode(password);
		User user = new User(email, encodePassword);
		if(userService.updatePassword(user) != 1) {
			return "failed";
		}
		
		return "success";
	}
	
	@GetMapping("/user/myinfo")
	@ApiOperation(value = "내 정보 출력")
	public User getMyInfo(@RequestParam(required = true) String email) throws Exception {
		System.out.println("-----------------/user/myinfo-----------------");
		System.out.println("email : " + email);
		
		User user = userService.getUserByEmail(email);

		return user;
	}
	
	@GetMapping("/user/auth")
	@ApiOperation(value = "계정 공개 여부 확인, 0:공개, 1: 비공개")
	public int getAuth(@RequestParam(required = true) int num) {
		System.out.println("-----------------/user/auth-----------------");
		System.out.println("num : " + num);
		
		return userService.getAuth(num);
	}
	
	
	@GetMapping("/user/userinfo")
	@ApiOperation(value = "유저 정보 출력")
	public User getUserInfo(@RequestParam(required = true) String myEmail,
							@RequestParam(required = true) String otherEmail) throws Exception {
		System.out.println("-----------------/user/userinfo-----------------");
		System.out.println("myEmail : " + myEmail);
		System.out.println("otherEmail : " + otherEmail);
		
		int myNum = userService.getNumByEmail(myEmail);
		int otherNum = userService.getNumByEmail(otherEmail);
		
		Search search = new Search(myNum, otherNum);
		
		if(searchService.isSearched(search) == null) {
			searchService.search(search);
		} else {
			searchService.updateSearch(search);
		}
		
		User user = userService.getUserByEmail(otherEmail);

		return user;
	}
	
	@GetMapping("/user/userinfo/{nickname}")
	@ApiOperation(value = "닉네임으로 유저 정보 출력")
	public User getUserInfoByNickname(@RequestParam(required = true) String nickname) throws Exception {
		System.out.println("-----------------/user/userinfo/{nickname}-----------------");
		System.out.println("nickname : " + nickname);
		
		User user= userService.getUserByNickname(nickname);
		
		return user;
	}
}
