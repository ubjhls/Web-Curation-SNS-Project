package com.ssafy.edu.vue.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.edu.vue.dto.Book;
import com.ssafy.edu.vue.dto.DepCountDto;
import com.ssafy.edu.vue.dto.DepartmentDto;
import com.ssafy.edu.vue.dto.DepartmentEmpDto;
import com.ssafy.edu.vue.dto.DeptEmpsDto;
import com.ssafy.edu.vue.dto.EmployeeDto;
import com.ssafy.edu.vue.dto.EmployeeDto2;
import com.ssafy.edu.vue.help.BoolResult;
import com.ssafy.edu.vue.help.NumberResult;
import com.ssafy.edu.vue.service.EmployeeService;
import com.ssafy.edu.vue.service.IBookService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

//http://localhost:8197/ssafyvue/swagger-ui.html
@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController //鍮꾨룞湲� 而⑦듃濡�
@RequestMapping("/api")
@Api(value="SSAFY", description="SSAFY Resouces Management 2019")
public class BookController {
	
	public static final Logger logger = LoggerFactory.getLogger(BookController.class);
	
	@Autowired
	private IBookService bookservice;

	//api�쓽 books
    @ApiOperation(value = "紐⑤뱺 梨낆쓽 �젙蹂대�� 諛섑솚�븳�떎.", response = List.class)
	@RequestMapping(value = "/books", method = RequestMethod.GET)
	public ResponseEntity<List<Book>> books() throws Exception {
		logger.info("1-------------findAllBooks-----------------------------"+new Date());
		List<Book> emps = bookservice.getBookList();
		if (emps.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Book>>(emps, HttpStatus.OK);
	}
    
  //@RequestBody  鍮꾨룞湲고븷�뻹 �벐�뒗嫄�
    @ApiOperation(value = "�븳 梨낆쓽 �젙蹂대�� 諛섑솚�븳�떎.", response = Book.class)
	@RequestMapping(value = "/detailbooks", method = RequestMethod.POST)
	public ResponseEntity<Book> detailbook(@RequestBody String isbn) throws Exception {
    	
		logger.info("2-------------detailBooks-----------------------------"+new Date());
		Book emps = bookservice.getBook(isbn);
		
		if (emps==null || emps.getIsbn()==null || emps.getIsbn()=="") {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Book>(emps, HttpStatus.OK);
	}
    
    
}
