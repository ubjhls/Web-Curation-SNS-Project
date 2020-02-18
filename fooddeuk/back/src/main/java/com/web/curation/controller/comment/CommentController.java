package com.web.curation.controller.comment;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.model.BasicResponse;
import com.web.curation.model.comment.Comment;
import com.web.curation.service.ICommentService;
import com.web.curation.service.IPostService;
import com.web.curation.service.IUserService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin("*")
@RestController
public class CommentController {
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private ICommentService commentService;
	
	@Autowired
	private IPostService postService;
	
	
	@GetMapping("/comment/comment")
	@ApiOperation(value = "댓글 가져오기")
	public Object getAllComment(@RequestParam(required = true) int postnum) throws Exception {
		log.info("GET : /comment/comment");
		
		List<Comment> list = commentService.getAllComment(postnum);
		BasicResponse result = new BasicResponse();
		
		result.data="success";
		
		if(list.size() == 0) {
			result.data = "empty";
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		
		for (int i = 0; i < list.size(); i++) {
			String nickname = userService.getNickname(list.get(i).getAuthor());
			list.get(i).setNickname(nickname);
		}
		
		result.object = list;
		result.status = true;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PostMapping("/comment/comment")
	@ApiOperation(value = "댓글 등록하기")
	public Object insertComment(@RequestParam(required = true) int postnum,
			@RequestParam(required = true) String email,
			@RequestParam(required = true) String comment) throws Exception {
		log.info("POST : /comment/comment");
		
		int myNum = userService.getNumByEmail(email);
		
		Comment c = new Comment(postnum, myNum, comment);
		
		if(commentService.insertComment(c) == 0 || postService.commentCountUp(postnum) == 0) {
			return "failed";
		}
		
		List<Comment> list = commentService.getAllComment(postnum);
		BasicResponse result = new BasicResponse();
		
		result.data="success";
		
		for (int i = 0; i < list.size(); i++) {
			String nick = userService.getNickname(list.get(i).getAuthor());
			list.get(i).setNickname(nick);
		}
		
		result.object = list;
		result.status = true;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@DeleteMapping("/comment/comment")
	@ApiOperation(value = "댓글 삭제하기")
	public Object deleteComment(@RequestParam(required = true) int num,
			@RequestParam(required = true) int postnum) throws Exception {
		log.info("DELETE : /comment/comment");
		
		BasicResponse result = new BasicResponse();
		if(commentService.deleteComment(num) == 0 || postService.commentCountDown(postnum) == 0) {
			result.data = "failed";
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		
		List<Comment> list = commentService.getAllComment(postnum);
		
		if(list.size() == 0) {
			result.data = "empty";
			result.object = list;
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		
		for (int i = 0; i < list.size(); i++) {
			String nick = userService.getNickname(list.get(i).getAuthor());
			list.get(i).setNickname(nick);
		}
		
		result.object = list;
		result.data="success";
		result.status = true;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/comment/count")
	@ApiOperation(value = "댓글 개수 가져오기")
	public int getCommentCount(@RequestParam(required = true) int postnum) throws Exception {
		log.info("GET : /comment/count");
		
		return commentService.getCommentCount(postnum);
	}
	
}
