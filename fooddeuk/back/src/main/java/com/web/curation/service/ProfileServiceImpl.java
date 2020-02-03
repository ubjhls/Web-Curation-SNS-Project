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
	
}
