package com.web.curation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.dao.ProfileDaoImpl;
import com.web.curation.model.profile.Profile;

@Service
public class ProfileServiceImpl implements IProfileService{

	@Autowired
	private ProfileDaoImpl profiledao;
	
	@Override
	public int createProfile(int num) {
		return profiledao.createProfile(num);
	}

	@Override
	public Profile getProfile(int num) {
		return profiledao.getProfile(num);
	}

	@Override
	public String getMyPlace(int num) {
		return profiledao.getMyPlace(num);
	}
	
	public int updatePlace(Profile profile) {
		return profiledao.updatePlace(profile);
	}
<<<<<<< HEAD

	@Override
	public String getPicture(int num) {
		return profiledao.getPicture(num);
	}
	
=======
	@Override
	public int insertPicture(String picture) {
		return profiledao.insertPicture(picture);
	}
>>>>>>> 97fe6e73d471b79ce126300c043d228c5906bf4f
}
