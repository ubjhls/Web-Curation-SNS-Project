package com.ssafy.edu.vue.service;

import java.util.List;

import com.ssafy.edu.vue.dto.Book;


//OCP -> ISP 
public interface IBookService {
	List<Book> getBookList ();

	Book getBook(String isbn);

	void updatebook(Book b);

	void savebook(Book b);

	void deletebook(String isbn);
}
