package com.web.curation.controller.post;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
	
<<<<<<< HEAD


=======
	@Autowired
	private IPostlikeService postlikeService;
	
>>>>>>> 9227773567b5509ff0b083f248adf90346437647
	@PostMapping("/post/post")
	@ApiOperation(value = "게시물 작성")
	public String insertPost(@RequestParam(required = true) String email,
							@RequestParam(required = true) String title,
							@RequestParam(required = true) String content,
							@RequestParam(required = true) String count_star,
							@RequestParam(required = true) String address,
							@RequestParam(required = true) String image) throws Exception {
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
	
}
