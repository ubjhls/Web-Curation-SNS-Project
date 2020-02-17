package com.web.curation.service;

import com.web.curation.model.profile.Profile;

public interface IProfileService {
	int createProfile(int num);
	Profile getProfile(int num);
	String getMyPlace(int num);
	int updatePlace(Profile profile);
<<<<<<< HEAD
	String getPicture(int num);
=======
	int insertPicture(String picture);
>>>>>>> 97fe6e73d471b79ce126300c043d228c5906bf4f
}
