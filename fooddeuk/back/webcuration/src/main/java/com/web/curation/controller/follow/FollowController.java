package com.web.curation.controller.follow;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.web.curation.model.alarm.Alarm;
import com.web.curation.model.follow.Follow;
import com.web.curation.model.user.User;
import com.web.curation.service.IAlarmService;
import com.web.curation.service.IFollowService;
import com.web.curation.service.IProfileService;
import com.web.curation.service.IUserService;
import com.web.curation.service.ProfileServiceImpl;

import io.swagger.annotations.ApiOperation;

@CrossOrigin("*")
@RestController
public class FollowController {
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IFollowService followService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IAlarmService alarmService;
	
	@Autowired
	private IProfileService profileService;
	
	
	@GetMapping("/follow/follow")
	@ApiOperation(value = "팔로우 하고 있는지 유무 확인")
	public int checkFollow(@RequestParam(required = true) String mynickname,
							@RequestParam(required = true) String nickname) throws Exception {
		log.info("GET : /follow/follow");
		
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
		log.info("GET : /follow/following");
		
		int num = userService.getNumByEmail(email);
		
		return followService.countFollowing(num);
	}
	
	@GetMapping("/follow/follower")
	@ApiOperation(value = "나를 팔로워 하는 사람의 수")
	public int countFollower(@RequestParam(required = true) String email) throws Exception {
		log.info("GET : /follow/follower");
		
		int num = userService.getNumByEmail(email);
		
		return followService.countFollower(num);
	}
	
	@PostMapping("/follow/follow")
	@ApiOperation(value = "팔로우 하기")
	public String follow(@RequestParam(required = true) String mynickname,
					@RequestParam(required = true) String nickname) throws Exception {
		log.info("POST : /follow/follow");
		
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
		alarm.setSender(userService.getEmail(myNum));
		alarm.setReceiver(userService.getEmail(otherNum));
		alarm.setReason(2);
		
		//알람 메세지저장
		alarmService.nonfollowSave(alarm);
		
		return "success";
	}
	
	@PostMapping("/follow/unFollow")
	@ApiOperation(value = "언 팔로우 하기")
	public String unFollow(@RequestParam(required = true) String mynickname,
					@RequestParam(required = true) String nickname) throws Exception {
		log.info("POST : /follow/unFollow");
		
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
		log.info("GET : /follow/getFollower/{num}");
		
		BasicResponse result = new BasicResponse();
		result.data="success";
		
		List<User> list = followService.getFollower(num);
		List<Integer> isFollow = new ArrayList<Integer>();
		
		for (int i = 0; i < list.size(); i++) {
			isFollow.add(followService.checkFollow(new Follow(num, list.get(i).getNum())));
		}
		
		if(list.size() == 0) {
			result.data = "failed";
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		
		result.object = list;
		result.object2 = isFollow;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/follow/getFollowing/{num}")
	@ApiOperation(value = "나를 팔로잉 하는 사람들 가져오기")
	public Object getFollowing(@RequestParam(required = true) int num) throws Exception {
		log.info("GET : /follow/getFollowing/{num}");
		
		BasicResponse result = new BasicResponse();
		result.data="success";
		
		List<User> list = followService.getFollowing(num);
		List<Integer> isFollowing = new ArrayList<Integer>();
		
		for (int i = 0; i < list.size(); i++) {
			isFollowing.add(followService.checkFollow(new Follow(num, list.get(i).getNum())));
			
		}
		if(list.size() == 0) {
			result.data = "failed";
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		
		result.object = list;
		result.object2 = isFollowing;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PostMapping("/follow/nonfollow")
	@ApiOperation(value = "비공개유저에게 팔로우 요청")
	public String nonfollow(@RequestParam(required = true) String mynickname,
					@RequestParam(required = true) String nickname) throws Exception {
		log.info("POST : /follow/nonfollow");
	
		Alarm alarm = new Alarm();
		int myNum = userService.getNumByNickname(mynickname);
		int otherNum = userService.getNumByNickname(nickname);
		alarm.setSender(userService.getEmail(myNum));
		alarm.setReceiver(userService.getEmail(otherNum));
		
		//비공개 사용자에게 팔로우 요쳥 = 1;
		alarm.setReason(1);
		
		//전에 요청이 보냈는지 확인(보낸사람, 받는사람, 컨펌이 0, 유형이1인게 없으면 => 보낸적이 없어)
		int check = alarmService.checkFollowAlarm(alarm);
		
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
		log.info("GET : /follow/requestlist");
		
		List<Alarm> list = alarmService.myalarmList(userService.getEmailByNickname(mynickname));
		
		for (int i = 0; i < list.size(); i++) {
			int num = userService.getNumByEmail(list.get(i).getSender());
			list.get(i).setPicture(profileService.getPicture(num));
			list.get(i).setSender(userService.getNickname(num));
			list.get(i).setReceiver(mynickname);
		}
	
		return list;
	}
	
	@PostMapping("/follow/followagree")
	@ApiOperation(value = "팔로우 동의하기")
	public String followagree(@RequestParam(required = true) String num, @RequestParam(required = true) String mynickname,
					@RequestParam(required = true) String nickname, @RequestParam(required = true) String agree) throws Exception {
		log.info("POST : /follow/followagree");
		
		int myNum = userService.getNumByNickname(mynickname);
		int otherNum = userService.getNumByNickname(nickname);
		if(agree.equals("1")) {
			
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
			alarm2.setSender(userService.getEmail(myNum));
			alarm2.setReceiver(userService.getEmail(otherNum));
			alarm2.setReason(2);
			alarmService.nonfollowSave(alarm2);
		}else {
			//알람저장
			Alarm alarm2 = new Alarm();
			alarm2.setSender(userService.getEmail(myNum));
			alarm2.setReceiver(userService.getEmail(otherNum));
			alarm2.setReason(3);
			alarmService.nonfollowSave(alarm2);
		}
		
		alarmService.changeConfirm(Integer.parseInt(num));
		
		return "success";
	}
	
	@GetMapping("/follow/alarmlist")
	@ApiOperation(value = "알람에서 알람 리스트")
	public List<Alarm> alarmtlist(@RequestParam(required = true) String mynickname) throws Exception {
		log.info("GET : /follow/alarmlist");
		
		List<Alarm> list = alarmService.alarmtlist(userService.getEmailByNickname(mynickname));
		List<Alarm> result = new ArrayList<>();
		
		for (int i = 0; i < list.size(); i++) {
			int num = userService.getNumByEmail(list.get(i).getSender());
			if(userService.getNickname(num).equals(mynickname)) {
				continue;
			}
			list.get(i).setPicture(profileService.getPicture(num));
			list.get(i).setSender(userService.getNickname(num));
			list.get(i).setReceiver(mynickname);
			result.add(list.get(i));
		}
		
		return result;
	}
	
	@PatchMapping("/follow/alarmconfirm")
	@ApiOperation(value = "알람에서 알람 리스트 확인")
	public String alarmconfirm(@RequestParam(required = true) String num) throws Exception {
		log.info("PATCH : /follow/alarmconfirm");
		
		alarmService.changeConfirm(Integer.parseInt(num));
		
		return "success";
	}
	
	
}
