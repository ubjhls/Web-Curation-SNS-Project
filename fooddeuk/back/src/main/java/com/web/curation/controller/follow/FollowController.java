package com.web.curation.controller.follow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.model.BasicResponse;
import com.web.curation.model.follow.Follow;
import com.web.curation.model.post.Post;
import com.web.curation.model.alarm.Alarm;
import com.web.curation.model.follow.Follow;
import com.web.curation.model.user.User;
import com.web.curation.service.IAlarmService;
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
	
	@Autowired
	private IAlarmService alarmService;
	
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
		
		Alarm alarm = new Alarm();
		alarm.setReceiver(nickname);
		alarm.setSender(mynickname);
		alarm.setReason(2);
		
		//알람 메세지저장
		alarmService.nonfollowSave(alarm);
		
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
	
	@PostMapping("/follow/nonfollow")
	@ApiOperation(value = "비공개유저에게 팔로우 요청")
	public String nonfollow(@RequestParam(required = true) String mynickname,
					@RequestParam(required = true) String nickname) throws Exception {
		System.out.println("-----------------nonfollow-----------------");
		System.out.println("myn : " + mynickname);
		System.out.println("n : " + nickname);
	
		Alarm alarm = new Alarm();
	
	    alarm.setReceiver(nickname);
		alarm.setSender(mynickname);
		
		//비공개 사용자에게 팔로우 요쳥 = 1;
		alarm.setReason(1);
		
		//전에 요청이 보냈는지 확인(보낸사람, 받는사람, 컨펌이 0, 유형이1인게 없으면 => 보낸적이 없어)
		int check = alarmService.checkFollowAlarm(alarm);
		System.out.println(check);
		
		if(check == 1) { //보낸적이 없어!
			
			//DB에 넣는다.
			alarmService.nonfollowSave(alarm);
			return "success";
		}else {
			return "failed";
		}
	}
	
	@GetMapping("/follow/requestlist")
	@ApiOperation(value = "알람에서 요청 리스트")
	public List<Alarm> requestlist(@RequestParam(required = true) String mynickname) throws Exception {
		System.out.println("-----------------requestlist-----------------");
		System.out.println("mynickname: " + mynickname);
		List<Alarm> list = alarmService.myalarmList(mynickname);
	
		System.out.println(list);
		
		return list;
	}
	
	@PostMapping("/follow/followagree")
	@ApiOperation(value = "팔로우 동의하기")
	public String followagree(@RequestParam(required = true) String num, @RequestParam(required = true) String mynickname,
					@RequestParam(required = true) String nickname, @RequestParam(required = true) String agree) throws Exception {
		System.out.println("-----------------followagree-----------------");
		System.out.println("mynickname : " + mynickname);
		System.out.println("nickname : " + nickname);
		System.out.println("agree : " + agree);
		System.out.println("num : " + num);
		
		if(agree.equals("1")) {
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
			
			//알람저장
			Alarm alarm2 = new Alarm();
			alarm2.setReceiver(nickname);
			alarm2.setSender(mynickname);
			alarm2.setReason(2);
			alarmService.nonfollowSave(alarm2);
		}else {
			//알람저장
			System.out.println("거절");
			Alarm alarm2 = new Alarm();
			alarm2.setSender(nickname);
			alarm2.setReceiver(mynickname);
			alarm2.setReason(3);
			alarmService.nonfollowSave(alarm2);
		}
		
		alarmService.changeConfirm(Integer.parseInt(num));
		
		
		
		return "success";
	}
	
	@GetMapping("/follow/alarmlist")
	@ApiOperation(value = "알람에서 알람 리스트")
	public List<Alarm> alarmtlist(@RequestParam(required = true) String mynickname) throws Exception {
		System.out.println("-----------------alarmlist----------------");
		System.out.println("mynickname: " + mynickname);
		List<Alarm> list = alarmService.alarmtlist(mynickname);
	
		System.out.println(list);
		
		return list;
	}
	
	@PatchMapping("/follow/alarmconfirm")
	@ApiOperation(value = "알람에서 알람 리스트 확인")
	public String alarmconfirm(@RequestParam(required = true) String num) throws Exception {
		System.out.println("-----------------alarmlist----------------");
		System.out.println("num: " + num);
		
		alarmService.changeConfirm(Integer.parseInt(num));
		
		return "success";
	}
	
	
}
