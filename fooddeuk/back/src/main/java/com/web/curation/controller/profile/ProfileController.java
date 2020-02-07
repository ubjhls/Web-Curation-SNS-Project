package com.web.curation.controller.profile;

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
import com.web.curation.model.profile.Profile;
import com.web.curation.service.IProfileService;
import com.web.curation.service.IUserService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin("*")
@RestController
public class ProfileController {
	
	@Autowired
	private IProfileService profileService;
	
	@Autowired
	private IUserService userService;
	
	@GetMapping("/profile/myplace")
	@ApiOperation(value = "내 지역 정보 가져오기")
	public Object getMyPlace(@RequestParam(required = true) String email) throws Exception {
		System.out.println("-----------------/profile/myplace-----------------");
		System.out.println("email : " + email);

		int num = userService.getNumByEmail(email);
		String place = profileService.getMyPlace(num);
		System.out.println("place : " + place);
		
		BasicResponse result = new BasicResponse();
		result.object = place.split("/");
		result.data="success";
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PatchMapping("/profile/place")
	@ApiOperation(value = "지역 정보 수정")
	public String updatePlace(@RequestParam(required = true) String email, 
							@RequestParam(required = true) String place1,
							@RequestParam(required = true) String place2) throws Exception {
		System.out.println("-----------------/profile/place-----------------");
		System.out.println("email : " + email);
		System.out.println("place1 : " + place1);
		System.out.println("place2 : " + place2);

		int num = userService.getNumByEmail(email);
		StringBuilder sb = new StringBuilder();
		sb.append(place1 + "/" + place2);
		
		Profile profile = new Profile(num, sb.toString());
		
		if(profileService.updatePlace(profile) != 1) {
			return "failed";
		}
		return "success";

	}
	
	@GetMapping("/profile/profile")
	@ApiOperation(value = "프로필 가져오기")
	public Profile getProfile(@RequestParam(required = true) String nickname) throws Exception {
		System.out.println("-----------------/profile/profile-----------------");
		System.out.println("nickname : " + nickname);
		
		int num = userService.getNumByNickname(nickname);
		Profile profile = profileService.getProfile(num);
		
		return profile;
	}
	
	
}
