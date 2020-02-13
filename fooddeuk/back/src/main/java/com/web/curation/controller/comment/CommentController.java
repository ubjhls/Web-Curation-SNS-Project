package com.web.curation.controller.comment;

import java.util.List;

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
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private ICommentService commentService;
	
	@Autowired
	private IPostService postService;
	
	@GetMapping("/comment/comment")
	@ApiOperation(value = "댓글 가져오기")
<<<<<<< HEAD
	public Object getAllComment(@RequestParam(required = true) int num) throws Exception {
		System.out.println("-----------------/comment/comment-----------------");
		System.out.println("num : " + num);
		
		List<Comment> list = commentService.getAllComment(num);
=======
	public Object getAllComment(@RequestParam(required = true) int postnum) throws Exception {
		System.out.println("-----------------/comment/comment-----------------");
		System.out.println("postnum : " + postnum);
		
		List<Comment> list = commentService.getAllComment(postnum);
>>>>>>> 9cf119a9e5d843d95f5b5df0997a4d5e97091c0c
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
		
		System.out.println(list);
		result.object = list;
		result.status = true;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PostMapping("/comment/comment")
	@ApiOperation(value = "댓글 등록하기")
<<<<<<< HEAD
	public Object insertComment(@RequestParam(required = true) int num,
			@RequestParam(required = true) String email,
			@RequestParam(required = true) String comment) throws Exception {
		System.out.println("-----------------/comment/comment-----------------");
		System.out.println("num : " + num);
=======
	public Object insertComment(@RequestParam(required = true) int postnum,
			@RequestParam(required = true) String email,
			@RequestParam(required = true) String comment) throws Exception {
		System.out.println("-----------------/comment/comment-----------------");
		System.out.println("postnum : " + postnum);
>>>>>>> 9cf119a9e5d843d95f5b5df0997a4d5e97091c0c
		System.out.println("email : " + email);
		System.out.println("comment : " + comment);
		
		int myNum = userService.getNumByEmail(email);
		
<<<<<<< HEAD
		Comment c = new Comment(num, myNum, comment);
		
		if(commentService.insertComment(c) == 0 || postService.commentCountUp(num) == 0) {
			return "failed";
		}
		
		List<Comment> list = commentService.getAllComment(num);
=======
		Comment c = new Comment(postnum, myNum, comment);
		
		if(commentService.insertComment(c) == 0 || postService.commentCountUp(postnum) == 0) {
			return "failed";
		}
		
		List<Comment> list = commentService.getAllComment(postnum);
>>>>>>> 9cf119a9e5d843d95f5b5df0997a4d5e97091c0c
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
<<<<<<< HEAD
			@RequestParam(required = true) String nickname,
			@RequestParam(required = true) String date) throws Exception {
		System.out.println("-----------------/comment/comment-----------------");
		System.out.println("num : " + num);
		System.out.println("nickname : " + nickname);
		System.out.println("date : " + date);
		
		int myNum = userService.getNumByNickname(nickname);
		
		Comment c = new Comment(num, myNum);
		c.setDate(date);
		
		if(commentService.deleteComment(c) == 0 || postService.commentCountDown(num) == 0) {
			return "failed";
		}
		
		List<Comment> list = commentService.getAllComment(num);
		BasicResponse result = new BasicResponse();
		
		result.data="success";
		
		if(list.size() == 0) {
			result.data = "empty";
=======
			@RequestParam(required = true) int postnum) throws Exception {
		System.out.println("-----------------/comment/comment-----------------");
		System.out.println("num : " + num);
		System.out.println("postnum : " + postnum);
		
		BasicResponse result = new BasicResponse();
		if(commentService.deleteComment(num) == 0 || postService.commentCountDown(postnum) == 0) {
			result.data = "failed";
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		
		List<Comment> list = commentService.getAllComment(postnum);
		
		if(list.size() == 0) {
			result.data = "empty";
			result.object = list;
>>>>>>> 9cf119a9e5d843d95f5b5df0997a4d5e97091c0c
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		
		for (int i = 0; i < list.size(); i++) {
			String nick = userService.getNickname(list.get(i).getAuthor());
			list.get(i).setNickname(nick);
		}
		
		result.object = list;
<<<<<<< HEAD
		result.status = true;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
=======
		result.data="success";
		result.status = true;
		System.out.println("삭제리턴확인");
		System.out.println(result.object);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/comment/count")
	@ApiOperation(value = "댓글 개수 가져오기")
	public int getCommentCount(@RequestParam(required = true) int postnum) throws Exception {
		System.out.println("-----------------/comment/count-----------------");
		System.out.println("postnum : " + postnum);
		
		return commentService.getCommentCount(postnum);
	}
	
>>>>>>> 9cf119a9e5d843d95f5b5df0997a4d5e97091c0c
}
