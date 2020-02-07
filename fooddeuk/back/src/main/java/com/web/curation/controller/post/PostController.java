package com.web.curation.controller.post;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
public class PostController {
	
	@Autowired
	private IPostService postService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IPostlikeService postlikeService;

	
	@PostMapping("/post/post")
	@ApiOperation(value = "게시물 작성")
	public String insertPost(@RequestParam(required = true) String email,
							@RequestParam(required = true) String title,
							@RequestParam(required = true) String content,
							@RequestParam(required = true) String count_star,
							@RequestParam(required = false) String address,
							@RequestParam(required = false) String image) throws Exception {
		System.out.println("-----------------/post/post-----------------");
		System.out.println("email : " + email);
		System.out.println("title : " + title);
		System.out.println("content : " + content);
		System.out.println("count_star : " + count_star);
		System.out.println("address : " + address);
		System.out.println("image : " + image);
		
		int author = userService.getNumByEmail(email);
		int star = Integer.parseInt(count_star);
		Post post = new Post(author, title, content, star, address);
		post.setImage(image);
		
		if(postService.insertPost(post) != 1) {
			return "failed";
		}
		
		return "success";
	}
	
	@GetMapping("/post/post/{num}")
	@ApiOperation(value = "게시물 가져오기")
	public Object getMyPost(@RequestParam(required = true) int num,
			@RequestParam(required = true) String email) throws Exception {
		System.out.println("-----------------/post/post/{num}-----------------");
		System.out.println("num : " + num);
		System.out.println("email : " + email);
		
		BasicResponse result = new BasicResponse();
		result.data="success";
		
		List<Post> list = postService.getAllPost(num);
		
		if(list.size() == 0) {
			result.data = "failed";
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		
		int myNum = userService.getNumByEmail(email);
		
		for (int i = 0; i < list.size(); i++) {
			Postlike like = new Postlike(list.get(i).getNum(), myNum);
			if(postlikeService.checkLike(like) != 0) {
				list.get(i).setIslike(1);
			}
		}
		
		System.out.println(list);
		result.status=true;
		result.object = list;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	
	@GetMapping("/post/mypost")
	@ApiOperation(value = "내가 게시한 게시물 가져오기")
	public Object getMyPost(@RequestParam(required = true) int num) throws Exception {
		System.out.println("-----------------/post/mypost-----------------");
		System.out.println("num : " + num);
		
		BasicResponse result = new BasicResponse();
		result.data="success";
		
		List<Post> list = postService.getMyPost(num);
		
		if(list.size() == 0) {
			result.data = "failed";
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		
		System.out.println(list);
		result.status=true;
		result.object = list;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/post/mylikepost")
	@ApiOperation(value = "내가 좋아요 누른 게시물 가져오기")
	public Object getMyLikePost(@RequestParam(required = true) int num) throws Exception {
		System.out.println("-----------------/post/mylikepost-----------------");
		System.out.println("num : " + num);
		
		BasicResponse result = new BasicResponse();
		result.data="success";
		
		List<Post> list = postService.getMyLikePost(num);
		
		if(list.size() == 0) {
			result.data = "failed";
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		     
		System.out.println(list);
		result.status=true;
		result.object = list;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/post/mycurationpost")
	@ApiOperation(value = "내 큐레이션 지역 게시물 가져오기")
	public Object getMyCurationPost(@RequestParam(required = true) int num) throws Exception {
		System.out.println("-----------------/post/mycurationpost-----------------");
		System.out.println("num : " + num);
		
		BasicResponse result = new BasicResponse();
		result.data="success";
		
		List<Post> list = postService.getMyLikePost(num);
		
		if(list.size() == 0) {
			result.data = "failed";
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		     
		System.out.println(list);
		result.status=true;
		result.object = list;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	
}
