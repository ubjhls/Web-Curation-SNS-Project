package com.web.curation.controller.post;

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
import com.web.curation.service.IPostService;
import com.web.curation.service.IUserService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin("*")
@RestController
public class PostController {
	
	@Autowired
	private IPostService postService;
	
	@Autowired
	private IUserService userService;
	
	@PostMapping("/post/post")
	@ApiOperation(value = "게시물 작성")
	public String insertPost(@RequestParam(required = true) String email,
							@RequestParam(required = true) String title,
							@RequestParam(required = true) String content,
							@RequestParam(required = true) String count_star,
							@RequestParam(required = true) String address) throws Exception {
		System.out.println("-----------------/post/post-----------------");
		System.out.println("email : " + email);
		System.out.println("title : " + title);
		System.out.println("content : " + content);
		System.out.println("count_star : " + count_star);
		System.out.println("address : " + address);

		int author = userService.getNumByEmail(email);
		int star = Integer.parseInt(count_star);
		
		Post post = new Post(author, title, content, star, address);
		
		if(postService.insertPost(post) != 1) {
			return "failed";
		}
		
		return "success";
	}
	
	@GetMapping("/post/post/{num}")
	@ApiOperation(value = "게시물 가져오기")
	public Object getMyPost(@RequestParam(required = true) int num) throws Exception {
		System.out.println("-----------------/post/post/{num}-----------------");
		System.out.println("num : " + num);
		
		BasicResponse result = new BasicResponse();
		result.data="success";
		
		List<Post> list = postService.getAllPost(num);
		System.out.println(list);
		
		if(list.size() == 0) {
			result.data = "failed";
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		
		result.object = list;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
}
