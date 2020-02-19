package com.web.curation.controller.profile;

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
import com.web.curation.model.profile.Profile;
import com.web.curation.service.IProfileService;
import com.web.curation.service.IUserService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin("*")
@RestController
public class ProfileController {
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IProfileService profileService;
	
	@Autowired
	private IUserService userService;
	
	@GetMapping("/profile/myplace")
	@ApiOperation(value = "내 지역 정보 가져오기")
	public Object getMyPlace(@RequestParam(required = true) String email) throws Exception {
		log.info("GET : /profile/myplace");

		int num = userService.getNumByEmail(email);
		String place = profileService.getMyPlace(num);
		
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
		log.info("PATCH : /profile/place");

		int num = userService.getNumByEmail(email);
		StringBuilder sb = new StringBuilder();
		
		if(place1.equals("없음")) {
			place2 = "없음";
		}
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
		log.info("GET : /profile/profile");
		
		int num = userService.getNumByNickname(nickname);
		Profile profile = profileService.getProfile(num);
		
		return profile;
	}
	
	@GetMapping("/profile/picture")
	@ApiOperation(value = "회원 사진 가져오기")
	public String getPicture(@RequestParam(required = true) int num) throws Exception {
		log.info("GET : /profile/picture");
		
		return profileService.getPicture(num);
	}
	
	@PostMapping("/profile/insertPicture")
	@ApiOperation(value = "프로필 사진 등록하기")
	public Object insertPicture(@RequestParam(required = true) int num,
								@RequestParam(required = true) String picture) throws Exception {
		log.info("POST : /profile/insertPicture");
		
		Profile profile = new Profile();
		profile.setNum(num);
		profile.setPicture(picture);
		BasicResponse result = new BasicResponse();
		
		if(profileService.updatePicture(profile)==0) {
			result.data="failed";
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		
		result.data="success";
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/profile/deletePicture")
	@ApiOperation(value = "프로필 사진 삭제하기")
	public Object deletePicture(@RequestParam(required = true) int num) throws Exception {
		log.info("GET : /profile/deletePicture");

		BasicResponse result = new BasicResponse();
		
		profileService.deletePicture(num);
		result.data="sueccess";
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}
