package com.web.curation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.dao.SearchDaoImpl;
import com.web.curation.model.post.Post;
import com.web.curation.model.search.Search;
import com.web.curation.model.user.User;

@Service
public class SearchServiceImpl implements ISearchService {

	@Autowired
	private SearchDaoImpl searchdao;
	
	@Override
	public List<Search> getAllSearch(int num) {
		return searchdao.getAllSearch(num);
	}
	
	@Override
	public String getNickname(int num) {
		return searchdao.getNickname(num);
	}

	@Override
	public List<User> searchNickname(String nickname) {
		return searchdao.searchNickname(nickname);
	}
	
	@Override
	public Search isSearched(Search search) {
		return searchdao.isSearched(search);
	}

	@Override
	public int search(Search search) {
		return searchdao.search(search);
	}
	
	@Override
	public int deleteSearch(Search search) {
		return searchdao.deleteSearch(search);
	}
	
	public int updateSearch(Search search) {
		return searchdao.updateSearch(search);
	}

	@Override
	public List<Post> searchFeed(String keyword) {
		return searchdao.searchFeed(keyword);
	}

}
