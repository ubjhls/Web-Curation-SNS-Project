package com.web.curation.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.web.curation.model.alarm.Alarm;

@Repository
public class AlarmDaoImpl {
	String ns="alarm.";
	
	@Autowired
	private SqlSession sqlSession;
	
	public void nonfollowSave(Alarm alarm) {
		sqlSession.selectOne(ns+"nonfollowSave", alarm);
	}

	public int checkFollowAlarm(Alarm alarm) {
		Alarm alarm2= sqlSession.selectOne(ns+"checkFollowAlarm", alarm);
		
		if(alarm2==null) {
			return 1;
		}else {
			return 0;
		}
	}

	public List<Alarm> myalarmList(String num) {
		return sqlSession.selectList(ns+"myalarmList", num);
	}

	public void changeConfirm(int num) {
		sqlSession.update(ns+"changeConfirm", num);
	}

	public List<Alarm> alarmtlist(String mynickname) {
		return sqlSession.selectList(ns+"alarmtlist", mynickname);
	}
	

}
