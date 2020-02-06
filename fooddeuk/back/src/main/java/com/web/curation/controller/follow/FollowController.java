package com.web.curation.controller.follow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.model.BasicResponse;
import com.web.curation.model.follow.Follow;
import com.web.curation.model.post.Post;
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
	
	@GetMapping("/follow/follow")
	@ApiOperation(value = "팔로우 하고 있는지 유무 확인")
	public int checkFollow(@RequestParam(required = true) String mynickname,
							@RequestParam(required = true) String nickname) throws Exception {
		System.out.println("-----------------/follow/follow-----------------");
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
	
	@GetMapping("/follow/following")
	@ApiOperation(value = "내가 팔로우 하는 사람의 수")
	public int countFollowing(@RequestParam(required = true) String email) throws Exception {
		System.out.println("-----------------/follow/following-----------------");
		System.out.println("email : " + email);
		
		int num = userService.getNumByEmail(email);
		
		return followService.countFollowing(num);
	}
	
	@GetMapping("/follow/follower")
	@ApiOperation(value = "나를 팔로워 하는 사람의 수")
	public int countFollower(@RequestParam(required = true) String email) throws Exception {
		System.out.println("-----------------/follow/follower-----------------");
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
		if(followService.followerUp(otherNum) != 1) {
			return "failed";
		}
		if(followService.followingUp(myNum) != 1) {
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
		if(followService.followerDown(otherNum) != 1) {
			return "failed";
		}
		if(followService.followingDown(myNum) != 1) {
			return "failed";
		}
		
		return "success";
	}
	@GetMapping("/follow/getFollower/{num}")
	@ApiOperation(value = "나를 팔로우 하는 사람들 가져오기")
	public Object getFollower(@RequestParam(required = true) int num) throws Exception {
		System.out.println("-----------------getFollower-----------------");
		System.out.println("num : " + num);
		BasicResponse result = new BasicResponse();
		result.data="success";
		
		List<Follow> list = followService.getFollower(num);
		
		if(list.size() == 0) {
			result.data = "failed";
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		
		result.object = list;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	@GetMapping("/follow/getFollowing/{num}")
	@ApiOperation(value = "나를 팔로잉 하는 사람들 가져오기")
	public Object getFollowing(@RequestParam(required = true) int num) throws Exception {
		System.out.println("-----------------getFollowing-----------------");
		System.out.println("num : " + num);
		BasicResponse result = new BasicResponse();
		result.data="success";
		
		List<Follow> list = followService.getFollowing(num);
		
		if(list.size() == 0) {
			result.data = "failed";
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		
		result.object = list;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
