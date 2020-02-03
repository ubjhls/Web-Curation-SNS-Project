package com.web.curation.controller.comment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.dao.CommentDaoImpl;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.comment.Comment;

import io.swagger.annotations.ApiOperation;

@CrossOrigin("*")
@RestController
public class CommentController {
	
	@Autowired
	private CommentDaoImpl commentDao;
	
	@GetMapping("/comment/comment")
	@ApiOperation(value = "댓글 가져오기")
	public Object getAllComment(@RequestParam(required = true) int num) throws Exception {
		System.out.println("-----------------/comment/comment-----------------");
		System.out.println("num : " + num);
		
		List<Comment> list = commentDao.getAllComment(num);
		BasicResponse result = new BasicResponse();
		
		result.data="success";
		
		if(list.size() == 0) {
			result.data = "failed";
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		System.out.println(list);
		
		result.object = list;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
}
