package com.web.curation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.dao.StatisticsDaoImpl;

@Service
public class StatisticsServiceImpl implements IStatisticsService {

	@Autowired
	private StatisticsDaoImpl statsdao;
	
	@Override
	public int getPostCount(int num) {
		return statsdao.getPostCount(num);
	}

	@Override
	public int getScrapCount(int num) {
		return statsdao.getScrapCount(num);
	}

	@Override
	public int getLikeCount(int num) {
		return statsdao.getLikeCount(num);
	}

	@Override
	public int getLikeClickCount(int num) {
		return statsdao.getCommentWriteCount(num);
	}

	@Override
	public int getCommentCount(int num) {
		return statsdao.getCommentCount(num);
	}

	@Override
	public int getCommentWriteCount(int num) {
		return statsdao.getCommentWriteCount(num);
	}

}
