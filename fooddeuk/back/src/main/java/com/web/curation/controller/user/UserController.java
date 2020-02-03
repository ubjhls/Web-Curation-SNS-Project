package com.web.curation.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.model.search.Search;
import com.web.curation.model.user.User;
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
	
	@PostMapping("/user/updateUser")
	@ApiOperation(value = "회원 정보 수정")
	public String updateUser(@RequestParam(required = true) String email,
							@RequestParam(required = true) String nickname, 
							@RequestParam(required = true) String intro, 
							@RequestParam(required = true) int auth) throws Exception {
		System.out.println("-----------------updateUser-----------------");
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
	
	@PostMapping("/user/updateNewPassword")
	@ApiOperation(value = "비밀번호 수정")
	public String updateNewPassword(@RequestParam(required = true) String email, 
								@RequestParam(required = true) String password) throws Exception {
		System.out.println("-----------------updateNewPassword-----------------");
		System.out.println("email : " + email);
		System.out.println("password : " + password);
		
		User user = new User(email, password);

		if(userService.updateNewPassword(user) != 1) {
			return "failed";
		}
		
		return "success";
	}
	
	@PostMapping("/user/getMyInfo")
	@ApiOperation(value = "내 정보 출력")
	public User getMyInfo(@RequestParam(required = true) String email) throws Exception {
		System.out.println("-----------------getMyInfo-----------------");
		System.out.println("email : " + email);
		
		User user = userService.getUserByEmail(email);

		return user;
	}
	
	@PostMapping("/user/getUserInfo")
	@ApiOperation(value = "유저 정보 출력")
	public User getUserInfo(@RequestParam(required = true) String myEmail,
							@RequestParam(required = true) String otherEmail) throws Exception {
		System.out.println("-----------------getUserInfo-----------------");
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
	
	@PostMapping("/user/getUserInfoByNickname")
	@ApiOperation(value = "닉네임으로 유저 정보 출력")
	public User getUserInfoByNickname(@RequestParam(required = true) String nickname) throws Exception {
		System.out.println("-----------------getUserInfoByNickname-----------------");
		System.out.println("nickname : " + nickname);
		
		User user= userService.getUserByNickname(nickname);
		
		return user;
	}
}
