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
	
	@Override
	public int updatePlace(Profile profile) {
		return profiledao.updatePlace(profile);
	}
	
	@Override
	public String getPicture(int num) {
		return profiledao.getPicture(num);
	}
	
	@Override
	public int updatePicture(Profile profile) {
		return profiledao.updatePicture(profile);
	}

	@Override
	public int deletePicture(int num) {
		return profiledao.deletePicture(num);
	}
}
