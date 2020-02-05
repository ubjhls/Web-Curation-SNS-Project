package com.web.curation.controller.comment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.model.BasicResponse;
import com.web.curation.model.comment.Comment;
import com.web.curation.service.ICommentService;
import com.web.curation.service.IUserService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin("*")
@RestController
public class CommentController {
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private ICommentService commentService;
	
	@GetMapping("/comment/comment")
	@ApiOperation(value = "댓글 가져오기")
	public Object getAllComment(@RequestParam(required = true) int num) throws Exception {
		System.out.println("-----------------/comment/comment-----------------");
		System.out.println("num : " + num);
		
		List<Comment> list = commentService.getAllComment(num);
		BasicResponse result = new BasicResponse();
		
		result.data="success";
		
		if(list.size() == 0) {
			result.data = "failed";
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
	
}
