package com.web.curation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.dao.AlarmDaoImpl;
import com.web.curation.model.Alarm;

@Service
public class AlarmService implements IAlarmService {
	
	@Autowired
	private AlarmDaoImpl alarmdao;
	
	@Override
	public void nonfollowSave(Alarm alarm) {
		
		alarmdao.nonfollowSave(alarm);

	}

	@Override
	public int checkFollowAlarm(Alarm alarm) {
	
		return alarmdao.checkFollowAlarm(alarm);
	}

	@Override
	public List<Alarm> myalarmList(String num) {
		
		return alarmdao.myalarmList(num);
		
	}

	@Override
	public void changeConfirm(int num) {
		alarmdao.changeConfirm(num);
		
	}

	@Override
	public List<Alarm> alarmtlist(String mynickname) {
		
		return alarmdao.alarmtlist(mynickname);
	}


	

}
