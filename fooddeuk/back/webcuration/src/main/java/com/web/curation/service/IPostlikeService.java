package com.web.curation.service;

import com.web.curation.model.postlike.Postlike;

public interface IPostlikeService {
	int like(Postlike like);
	int unlike(Postlike like);
	int checkLike(Postlike like);
}
