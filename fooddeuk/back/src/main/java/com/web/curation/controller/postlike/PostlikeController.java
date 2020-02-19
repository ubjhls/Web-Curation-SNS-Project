package com.web.curation.controller.postlike;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.model.BasicResponse;
import com.web.curation.model.post.Post;
import com.web.curation.model.postlike.Postlike;
import com.web.curation.service.IPostService;
import com.web.curation.service.IPostlikeService;
import com.web.curation.service.IUserService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin("*")
@RestController
public class PostlikeController {
	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IUserService userService;
	
	@Autowired
	private IPostlikeService postlikeService;
	
	@Autowired
	private IPostService postService;
	
	@PostMapping("/postlike/like")
	@ApiOperation(value = "좋아요")
	public Object like(@RequestParam(required = true) int postnum,
							@RequestParam(required = true) String email) throws Exception {
		log.info("POST : /postlike/like");

		int myNum = userService.getNumByEmail(email);
		
		Postlike like = new Postlike(postnum, myNum);

		if(postlikeService.like(like) == 0 || postService.likeCountUp(postnum) == 0) {
			return "failed";
		}
		
		int author = postService.getAuthor(postnum);
		
		List<Post> list = postService.getAllPost(author);
		
		for (int i = 0; i < list.size(); i++) {
			like = new Postlike(list.get(i).getNum(), myNum);
			if(postlikeService.checkLike(like) == 1) {
				list.get(i).setIslike(1);
			} else {
				list.get(i).setIslike(0);
			}
		}
		
		BasicResponse result = new BasicResponse();
		
		result.status = true;
		result.object = list;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@DeleteMapping("/postlike/unlike")
	@ApiOperation(value = "좋아요 취소")
	public Object unlike(@RequestParam(required = true) int postnum,
							@RequestParam(required = true) String email) throws Exception {
		log.info("DELETE : /postlike/unlike");

		int myNum = userService.getNumByEmail(email);
		
		Postlike like = new Postlike(postnum, myNum);

		if(postlikeService.unlike(like) == 0 || postService.likeCountDown(postnum) == 0) {
			return "failed";
		}
		
		int author = postService.getAuthor(postnum);
		
		List<Post> list = postService.getAllPost(author);
		
		for (int i = 0; i < list.size(); i++) {
			like = new Postlike(list.get(i).getNum(), myNum);
			if(postlikeService.checkLike(like) != 1) {
				list.get(i).setIslike(0);
			} else {
				list.get(i).setIslike(1);
			}
		}
		
		BasicResponse result = new BasicResponse();
		
		result.status = true;
		result.object = list;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
}
