package com.web.curation.service;

public interface IStatisticsService {
	int getPostCount(int num);
	int getScrapCount(int num);
	int getLikeCount(int num);
	int getLikeClickCount(int num);
	int getCommentCount(int num);
	int getCommentWriteCount(int num);
}
