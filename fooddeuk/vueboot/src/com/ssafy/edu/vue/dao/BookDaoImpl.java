package com.ssafy.edu.vue.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.edu.vue.dto.Book;

@Repository
public class BookDaoImpl {
	
	String ns="ssafy.book.";
	@Autowired
	private SqlSession sqlSession;
	
	public List<Book> getBookList (){
		return sqlSession.selectList(ns+"getBookList");
	}
	public Book getBook(String isbn) {
		return sqlSession.selectOne(ns+"getBook",isbn);
	}
	public void updatebook(Book b) {
		sqlSession.update(ns+"updatebook",b);
	}
	public void savebook(Book b) {
		sqlSession.insert(ns+"savebook",b);
	}

	public void deletebook(String isbn) {
		sqlSession.delete(ns+"deletebook",isbn);
		
	}
	
}
