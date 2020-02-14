package com.web.curation.service;

import com.web.curation.model.profile.Profile;

public interface IProfileService {
	int createProfile(int num);
	Profile getProfile(int num);
	String getMyPlace(int num);
	int updatePlace(Profile profile);
	String getPicture(int num);
}
