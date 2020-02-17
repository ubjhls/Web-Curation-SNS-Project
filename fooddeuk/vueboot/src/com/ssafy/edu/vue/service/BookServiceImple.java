package com.ssafy.edu.vue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.edu.vue.dao.BookDaoImpl;
import com.ssafy.edu.vue.dto.Book;



@Service
public class BookServiceImple implements IBookService {
	
	@Autowired
	private BookDaoImpl bookdao;  //스프링이 필요할 때 생성 후 넣어줌 

	@Override
	@Transactional(readOnly=true)
	public List<Book> getBookList() {
		return bookdao.getBookList();
	}

	@Override
	@Transactional(readOnly=true)
	public Book getBook(String isbn) {
		return bookdao.getBook( isbn);
	}

	@Override
	@Transactional
	public void updatebook(Book b) {
		bookdao.updatebook(b);
	}

	@Override
	@Transactional
	public void savebook(Book b) {
		bookdao.savebook(b);
	}


	@Override
	public void deletebook(String isbn) {
		bookdao.deletebook(isbn);
		
	}

}
