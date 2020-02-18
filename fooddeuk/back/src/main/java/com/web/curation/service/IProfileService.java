package com.web.curation.service;

import com.web.curation.model.profile.Profile;

public interface IProfileService {
	int createProfile(int num);
	Profile getProfile(int num);
	String getMyPlace(int num);
	int updatePlace(Profile profile);
	int updatePicture(Profile profile);
	int deletePicture(int num);
	String getPicture(int num);
	
}
