package com.web.curation.service;

import java.util.List;

import com.web.curation.model.search.Search;
import com.web.curation.model.user.User;

public interface ISearchService {
	List<Search> getAllSearch(int num);
	String getNickname(int num);
	List<User> searchNickname(String email);
	Search isSearched(Search search);
	int search(Search search);
	int deleteSearch(Search search);
	int updateSearch(Search search);
}
