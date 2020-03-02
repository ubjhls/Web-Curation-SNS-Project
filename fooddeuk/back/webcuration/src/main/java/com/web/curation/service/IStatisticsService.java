package com.web.curation.service;

import com.web.curation.model.statistics.Statistics;

public interface IStatisticsService {
	int getPostCount(int num);
	int getScrapCount(int num);
	int getLikeCount(int num);
	int getLikeClickCount(int num);
	int getCommentCount(int num);
	int getCommentWriteCount(int num);
	int getPostCountForDate(Statistics stats);
	int getScrapCountForDate(Statistics stats);
	int getLikeCountForDate(Statistics stats);
	int getLikeClickCountForDate(Statistics stats);
	int getCommentCountForDate(Statistics stats);
	int getCommentWriteCountForDate(Statistics stats);
}
