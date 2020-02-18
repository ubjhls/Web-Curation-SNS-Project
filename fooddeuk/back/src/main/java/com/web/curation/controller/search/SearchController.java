package com.web.curation.controller.search;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.model.BasicResponse;
import com.web.curation.model.post.Post;
import com.web.curation.model.postlike.Postlike;
import com.web.curation.model.search.Search;
import com.web.curation.model.user.User;
import com.web.curation.service.IPostlikeService;
import com.web.curation.service.ISearchService;
import com.web.curation.service.IUserService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin("*")
@RestController
public class SearchController {
	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IUserService userService;
	
	@Autowired
	private ISearchService searchService;
	
	@Autowired
	private IPostlikeService postlikeService;
	
	@GetMapping("/search/all")
	@ApiOperation(value = "최근 검색")
	public List<String> getAllSearch(@RequestParam(required = true) String email) throws Exception {
		log.info("GET : /search/all");
		
		int num = userService.getNumByEmail(email);
		List<Search> list = searchService.getAllSearch(num);
		List<String> result = new ArrayList<>();
		
		for (int i = 0; i < list.size(); i++) {
			String nickname = searchService.getNickname(list.get(i).getWho());
			result.add(nickname);
		}
		
		return result;
	}
	
	@GetMapping("/search/nickname")
	@ApiOperation(value = "닉네임 검색")
	public Object searchNickname(@RequestParam(required = true) String nickname) throws Exception {
		log.info("GET : /search/nickname");
		
		BasicResponse result = new BasicResponse();
		result.data="success";
		
		if(nickname == null || nickname == "") {
			result.data = "failed";
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		
		List<User> list = searchService.searchNickname(nickname);
		
		if(list.size() == 0) {
			result.data = "empty";
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		
		result.object = list;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/search/feed")
	@ApiOperation(value = "피드 검색")
	public Object searchFeed(@RequestParam(required = true) String keyword, 
			@RequestParam(required = true) String email) throws Exception {
		log.info("GET : /search/feed");
		BasicResponse result = new BasicResponse();
		result.data="success";
		
		if(keyword == null || keyword == "") {
			result.data = "failed";
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		
		List<Post> list = searchService.searchFeed(keyword);
		
		if(list.size() == 0) {
			result.data = "empty";
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		
		int myNum = userService.getNumByEmail(email);
		for (int i = 0; i < list.size(); i++) {
			Postlike like = new Postlike(list.get(i).getNum(), myNum);
			if(postlikeService.checkLike(like) != 0) {
				list.get(i).setIslike(1);
			}
			String nickname = userService.getNickname(list.get(i).getAuthor());
			list.get(i).setNickname(nickname);
			// list.get(i).setPicture(postService.getPicture(num));
		}
		
		result.object = list;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}	
	
	@DeleteMapping("/search/search")
	@ApiOperation(value = "검색 삭제")
	public String deleteSearch(@RequestParam(required = true) String myNick, 
									@RequestParam(required = true) String otherNick) throws Exception {
		log.info("DELETE : /search/search");
		
		int myNum = userService.getNumByNickname(myNick);
		int otherNum = userService.getNumByNickname(otherNick);
		
		Search search = new Search(myNum, otherNum);
		if(searchService.deleteSearch(search) != 1) {
			return "failed";
		}
		
		return "success";
	}
	
}
