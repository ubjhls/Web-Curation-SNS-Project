package com.web.curation.service;

import java.util.List;

import com.web.curation.model.Alarm;

public interface IAlarmService {
	void nonfollowSave(Alarm alarm);

	int checkFollowAlarm(Alarm alarm);

	List<Alarm> myalarmList(String mynickname);

	void changeConfirm(int i);

	List<Alarm> alarmtlist(String mynickname);
}
