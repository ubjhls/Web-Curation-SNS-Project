package com.web.curation.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.web.curation.model.search.Search;
import com.web.curation.model.user.User;

@Repository
public class SearchDaoImpl {

	String ns="search.";
	@Autowired
	private SqlSession sqlSession;
	
	public List<Search> getAllSearch(int num) {
		return sqlSession.selectList(ns+"getAllSearch", num);
	}
	
	public String getNickname(int num) {
		return sqlSession.selectOne(ns+"getNickname", num);
	}
	
	public List<User> searchNickname(String nickname) {
		return sqlSession.selectList(ns+"searchNickname", nickname);
	}
	
	public Search isSearched(Search search) {
		return sqlSession.selectOne(ns+"isSearched", search);
	}
	
	public int search(Search search) {
		return sqlSession.insert(ns+"search", search);
	}
	
	public int deleteSearch(Search search) {
		return sqlSession.delete(ns+"deleteSearch", search);
	}
	
	public int updateSearch(Search search) {
		return sqlSession.update(ns+"updateSearch", search);
	}
	
}
