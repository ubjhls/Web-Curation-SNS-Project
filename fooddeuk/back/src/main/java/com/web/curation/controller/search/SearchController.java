package com.web.curation.controller.search;

import java.util.ArrayList;
import java.util.List;

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
import com.web.curation.model.search.Search;
import com.web.curation.model.user.User;
import com.web.curation.service.ISearchService;
import com.web.curation.service.IUserService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin("*")
@RestController
public class SearchController {
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private ISearchService searchService;
	
	@GetMapping("/search/all")
	@ApiOperation(value = "최근 검색")
	public List<String> getAllSearch(@RequestParam(required = true) String email) throws Exception {
		System.out.println("-----------------/search/all-----------------");
		System.out.println("email : " + email);
		
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
		System.out.println("-----------------/search/nickname-----------------");
		System.out.println("nickname : " + nickname);
		
		BasicResponse result = new BasicResponse();
		result.data="success";
		
		if(nickname == null || nickname == "") {
			result.data = "failed";
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		
		List<User> list = searchService.searchNickname(nickname);
		System.out.println(list);
		
		if(list.size() == 0) {
			result.data = "empty";
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		
		result.object = list;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/search/feed")
	@ApiOperation(value = "피드 검색")
	public Object searchFeed(@RequestParam(required = true) String keyword) throws Exception {
		System.out.println("-----------------/search/feed-----------------");
		System.out.println("keyword : " + keyword);
		
		
		BasicResponse result = new BasicResponse();
		result.data="success";
		
		if(keyword == null || keyword == "") {
			result.data = "failed";
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		
		List<Post> list = searchService.searchFeed(keyword);
		System.out.println(list);
		
		if(list.size() == 0) {
			result.data = "empty";
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		
		result.object = list;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}	
	
	@DeleteMapping("/search/search")
	@ApiOperation(value = "검색 삭제")
	public String deleteSearch(@RequestParam(required = true) String myNick, 
									@RequestParam(required = true) String otherNick) throws Exception {
		System.out.println("-----------------/search/search-----------------");
		System.out.println("myNick : " + myNick);
		System.out.println("otherNick : " + otherNick);
		
		int myNum = userService.getNumByNickname(myNick);
		int otherNum = userService.getNumByNickname(otherNick);
		
		Search search = new Search(myNum, otherNum);
		if(searchService.deleteSearch(search) != 1) {
			return "failed";
		}
		
		return "success";
	}
	
}
