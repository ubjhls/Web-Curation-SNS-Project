package com.web.curation.controller.follow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.model.follow.Follow;
import com.web.curation.service.IFollowService;
import com.web.curation.service.IUserService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin("*")
@RestController
public class FollowController {
	
	@Autowired
	private IFollowService followService;
	
	@Autowired
	private IUserService userService;
	
	@PostMapping("/follow/checkFollow")
	@ApiOperation(value = "팔로우 하고 있는지 유무 확인")
	public int checkFollow(@RequestParam(required = true) String mynickname,
							@RequestParam(required = true) String nickname) throws Exception {
		System.out.println("-----------------checkFollow-----------------");
		System.out.println("mynickname : " + mynickname);
		System.out.println("nickname : " + nickname);
		
		int myNum = userService.getNumByNickname(mynickname);
		int otherNum = userService.getNumByNickname(nickname);
		if(myNum == otherNum) {
			return -1;
		}
		
		Follow follow = new Follow(myNum, otherNum);
		
		return followService.checkFollow(follow);
	}
	
	
	
	@PostMapping("/follow/countFollowing")
	@ApiOperation(value = "내가 팔로우 하는 사람의 수")
	public int countFollowing(@RequestParam(required = true) String email) throws Exception {
		System.out.println("-----------------countFollowing-----------------");
		System.out.println("email : " + email);
		
		int num = userService.getNumByEmail(email);
		
		return followService.countFollowing(num);
	}
	
	@PostMapping("/follow/countFollower")
	@ApiOperation(value = "나를 팔로워 하는 사람의 수")
	public int countFollower(@RequestParam(required = true) String email) throws Exception {
		System.out.println("-----------------countFollower-----------------");
		System.out.println("email : " + email);
		
		int num = userService.getNumByEmail(email);
		
		return followService.countFollower(num);
	}
	
	
	@PostMapping("/follow/follow")
	@ApiOperation(value = "팔로우 하기")
	public String follow(@RequestParam(required = true) String mynickname,
					@RequestParam(required = true) String nickname) throws Exception {
		System.out.println("-----------------follow-----------------");
		System.out.println("mynickname : " + mynickname);
		System.out.println("nickname : " + nickname);
		
		int myNum = userService.getNumByNickname(mynickname);
		int otherNum = userService.getNumByNickname(nickname);
		
		Follow follow = new Follow(myNum, otherNum);
		
		if(followService.follow(follow) != 1) {
			return "failed";
		}
		
		return "success";
	}
	
	@PostMapping("/follow/unFollow")
	@ApiOperation(value = "언 팔로우 하기")
	public String unFollow(@RequestParam(required = true) String mynickname,
					@RequestParam(required = true) String nickname) throws Exception {
		System.out.println("-----------------unFollow-----------------");
		System.out.println("mynickname : " + mynickname);
		System.out.println("nickname : " + nickname);
		
		int myNum = userService.getNumByNickname(mynickname);
		int otherNum = userService.getNumByNickname(nickname);
		
		Follow follow = new Follow(myNum, otherNum);
		
		if(followService.unFollow(follow) != 1) {
			return "failed";
		}
		
		return "success";
	}
	
}
