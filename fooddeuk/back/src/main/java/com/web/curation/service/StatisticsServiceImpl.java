package com.web.curation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.dao.StatisticsDaoImpl;
import com.web.curation.model.statistics.Statistics;

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

	@Override
	public int getPostCountForDate(Statistics stats) {
		return statsdao.getPostCountForDate(stats);
	}

	@Override
	public int getScrapCountForDate(Statistics stats) {
		return statsdao.getScrapCountForDate(stats);
	}

	@Override
	public int getLikeCountForDate(Statistics stats) {
		return statsdao.getLikeCountForDate(stats);
	}

	@Override
	public int getLikeClickCountForDate(Statistics stats) {
		return statsdao.getLikeClickCountForDate(stats);
	}

	@Override
	public int getCommentCountForDate(Statistics stats) {
		return statsdao.getCommentCountForDate(stats);
	}

	@Override
	public int getCommentWriteCountForDate(Statistics stats) {
		return statsdao.getCommentWriteCountForDate(stats);
	}

}
