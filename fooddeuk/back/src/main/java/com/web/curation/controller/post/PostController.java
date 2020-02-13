package com.web.curation.controller.post;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
<<<<<<< HEAD
=======
import org.springframework.web.bind.annotation.PatchMapping;
>>>>>>> 9cf119a9e5d843d95f5b5df0997a4d5e97091c0c
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.model.BasicResponse;
import com.web.curation.model.curation.Curation;
import com.web.curation.model.post.Post;
import com.web.curation.model.postlike.Postlike;
import com.web.curation.service.IPostService;
import com.web.curation.service.IPostlikeService;
import com.web.curation.service.IProfileService;
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
	
	@Autowired
	private IProfileService profileService;

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
<<<<<<< HEAD
		Post post = new Post(author, title, content, star, address);
		post.setImage(image);
=======
		
		Post post = new Post(author, title, content, star, address);
		if(!image.equals("null")) {
			post.setImage(image);
		}
>>>>>>> 9cf119a9e5d843d95f5b5df0997a4d5e97091c0c
		
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
<<<<<<< HEAD
	public Object getMyPost(@RequestParam(required = true) int num) throws Exception {
		System.out.println("-----------------/post/mypost-----------------");
		System.out.println("num : " + num);
=======
	public Object getMyPost(@RequestParam(required = true) String nickname) throws Exception {
		System.out.println("-----------------/post/mypost-----------------");
		System.out.println("nickname : " + nickname);
		
		int num = userService.getNumByNickname(nickname);
>>>>>>> 9cf119a9e5d843d95f5b5df0997a4d5e97091c0c
		
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
<<<<<<< HEAD
	public Object getMyLikePost(@RequestParam(required = true) int num) throws Exception {
		System.out.println("-----------------/post/mylikepost-----------------");
		System.out.println("num : " + num);
=======
	public Object getMyLikePost(@RequestParam(required = true) String nickname) throws Exception {
		System.out.println("-----------------/post/mylikepost-----------------");
		System.out.println("nickname : " + nickname);
		
		int num = userService.getNumByNickname(nickname);
>>>>>>> 9cf119a9e5d843d95f5b5df0997a4d5e97091c0c
		
		BasicResponse result = new BasicResponse();
		result.data="success";
		
		List<Post> list = postService.getMyLikePost(num);
<<<<<<< HEAD
		
=======
>>>>>>> 9cf119a9e5d843d95f5b5df0997a4d5e97091c0c
		if(list.size() == 0) {
			result.data = "nothing";
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
<<<<<<< HEAD
		     
=======
		
		for (int i = 0; i < list.size(); i++) {
			int postAuthor = list.get(i).getAuthor();
			String postNick = userService.getNickname(postAuthor);
			list.get(i).setNickname(postNick);
			Postlike like = new Postlike(list.get(i).getNum(), num);
			if(postlikeService.checkLike(like) != 0) {
				list.get(i).setIslike(1);
			}
		}
		
>>>>>>> 9cf119a9e5d843d95f5b5df0997a4d5e97091c0c
		System.out.println(list);
		result.status=true;
		result.object = list;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/post/mycurationpost")
	@ApiOperation(value = "내 큐레이션 지역 게시물 가져오기")
<<<<<<< HEAD
	public Object getMyCurationPost(@RequestParam(required = true) int num) throws Exception {
		System.out.println("-----------------/post/mycurationpost-----------------");
		System.out.println("num : " + num);
=======
	public Object getMyCurationPost(@RequestParam(required = true) String nickname) throws Exception {
		System.out.println("-----------------/post/mycurationpost-----------------");
		System.out.println("nickname : " + nickname);
		
		int num = userService.getNumByNickname(nickname);
>>>>>>> 9cf119a9e5d843d95f5b5df0997a4d5e97091c0c
		
		BasicResponse result = new BasicResponse();
		result.data="success";
		
		String temp = profileService.getMyPlace(num);
		String[] placeArr = temp.split("/");
		String place = placeArr[0] + " " + placeArr[1];
		System.out.println(place);
		
		List<Post> list = null;
		
		if(placeArr[0].equals("없음")) {
			result.data = "empty";
			return new ResponseEntity<>(result , HttpStatus.OK);
		} else if(placeArr[0].equals("전체")) {
			// 모든 피드 보여줌 && (공개 사용자 OR (비공개 사용자 && 팔로우))
			list = postService.getAllUserPost(num);
<<<<<<< HEAD
=======
			for (int i = 0; i < list.size(); i++) {
				int postAuthor = list.get(i).getAuthor();
				String postNick = userService.getNickname(postAuthor);
				list.get(i).setNickname(postNick);
				Postlike like = new Postlike(list.get(i).getNum(), num);
				if(postlikeService.checkLike(like) != 0) {
					list.get(i).setIslike(1);
				}
			}
>>>>>>> 9cf119a9e5d843d95f5b5df0997a4d5e97091c0c
			System.out.println("모든 피드 보여줌 && (공개 사용자 OR (비공개 사용자 && 팔로우))");
		} else {
			if(placeArr[1].equals("전체")) {
				// 그 지역의 모든 동네 정보 보여줌 && (공개 사용자 OR (비공개 사용자 && 팔로우))
				Curation curation = new Curation(num, placeArr[0]);
				list = postService.getMyCurationPost(curation);
<<<<<<< HEAD
=======
				for (int i = 0; i < list.size(); i++) {
					int postAuthor = list.get(i).getAuthor();
					String postNick = userService.getNickname(postAuthor);
					list.get(i).setNickname(postNick);
					Postlike like = new Postlike(list.get(i).getNum(), num);
					if(postlikeService.checkLike(like) != 0) {
						list.get(i).setIslike(1);
					}
				}
>>>>>>> 9cf119a9e5d843d95f5b5df0997a4d5e97091c0c
				System.out.println("그 지역의 모든 동네 정보 보여줌 && (공개 사용자 OR (비공개 사용자 && 팔로우))");
			} else {
				// 그 지역의 동네 정보 보여줌 && ((비공개 사용자 && 팔로우) OR공개 사용자 ))
				Curation curation = new Curation(num, place);
				list = postService.getMyCurationPost(curation);
<<<<<<< HEAD
=======
				for (int i = 0; i < list.size(); i++) {
					int postAuthor = list.get(i).getAuthor();
					String postNick = userService.getNickname(postAuthor);
					list.get(i).setNickname(postNick);
					Postlike like = new Postlike(list.get(i).getNum(), num);
					if(postlikeService.checkLike(like) != 0) {
						list.get(i).setIslike(1);
					}
				}
				System.out.println("그 지역의 동네 정보 보여줌 && ((비공개 사용자 && 팔로우) OR공개 사용자 ))");
			}
		}
		
		if(list.size() == 0) {
			result.data = "nothing";
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		     
		result.status=true;
		result.object = list;
		System.out.println(list);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/post/main")
	@ApiOperation(value = "메인화면 게시물 보여주기")
	public Object mainPost(@RequestParam(required = true) String nickname) throws Exception {
		System.out.println("-----------------/post/main-----------------");
		System.out.println("nickname : " + nickname);
		
		int num = userService.getNumByNickname(nickname);
		
		BasicResponse result = new BasicResponse();
		result.data="success";
		
		String temp = profileService.getMyPlace(num);
		String[] placeArr = temp.split("/");
		String place = placeArr[0] + " " + placeArr[1];
		System.out.println(place);
		
		List<Post> list = null;
		
		Post post = new Post();
		post.setAddress(place);
		post.setMynum(num);
		
		if(placeArr[0].equals("없음")) {
			list = postService.getMyFollowingPost(num);
			result.object = list;
			return new ResponseEntity<>(result , HttpStatus.OK);
		} else if(placeArr[0].equals("전체")) {
			// 모든 피드 보여줌 && (공개 사용자 OR (비공개 사용자 && 팔로우))
			list = postService.getAllUserPost(num);
			for (int i = 0; i < list.size(); i++) {
				int postAuthor = list.get(i).getAuthor();
				String postNick = userService.getNickname(postAuthor);
				list.get(i).setNickname(postNick);
				Postlike like = new Postlike(list.get(i).getNum(), num);
				if(postlikeService.checkLike(like) != 0) {
					list.get(i).setIslike(1);
				}
			}
			System.out.println("모든 피드 보여줌 && (공개 사용자 OR (비공개 사용자 && 팔로우))");
		} else {
			if(placeArr[1].equals("전체")) {
				// 그 지역의 모든 동네 정보 보여줌 && (공개 사용자 OR (비공개 사용자 && 팔로우))
				Curation curation = new Curation(num, placeArr[0]);
				list = postService.getMyMainPost(curation);
				for (int i = 0; i < list.size(); i++) {
					int postAuthor = list.get(i).getAuthor();
					String postNick = userService.getNickname(postAuthor);
					list.get(i).setNickname(postNick);
					Postlike like = new Postlike(list.get(i).getNum(), num);
					if(postlikeService.checkLike(like) != 0) {
						list.get(i).setIslike(1);
					}
				}
				System.out.println("그 지역의 모든 동네 정보 보여줌 && (공개 사용자 OR (비공개 사용자 && 팔로우))");
			} else {
				// 그 지역의 동네 정보 보여줌 && ((비공개 사용자 && 팔로우) OR공개 사용자 ))
				Curation curation = new Curation(num, place);
				list = postService.getMyMainPost(curation);
				for (int i = 0; i < list.size(); i++) {
					int postAuthor = list.get(i).getAuthor();
					String postNick = userService.getNickname(postAuthor);
					list.get(i).setNickname(postNick);
					Postlike like = new Postlike(list.get(i).getNum(), num);
					if(postlikeService.checkLike(like) != 0) {
						list.get(i).setIslike(1);
					}
				}
>>>>>>> 9cf119a9e5d843d95f5b5df0997a4d5e97091c0c
				System.out.println("그 지역의 동네 정보 보여줌 && ((비공개 사용자 && 팔로우) OR공개 사용자 ))");
			}
		}
		
		if(list.size() == 0) {
			result.data = "nothing";
<<<<<<< HEAD
=======
			result.object = list; 
>>>>>>> 9cf119a9e5d843d95f5b5df0997a4d5e97091c0c
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		     
		result.status=true;
		result.object = list;
		System.out.println(list);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PostMapping("/post/scrap")
	@ApiOperation(value = "게시물 스크랩하기")
	public String scrapPost(@RequestParam(required = true) int num,
							@RequestParam(required = true) int postnum,
							@RequestParam(required = true) int author,
							@RequestParam(required = true) String date) throws Exception {
		System.out.println("-----------------/post/scrap-----------------");
		System.out.println("num : " + num);
		System.out.println("postnum : " + postnum);
		System.out.println("author : " + author);
		System.out.println("date : " + date);
		
		Post temp = new Post(postnum, author, date);
		Post scrapPost = postService.getPost(temp); // 해당 포스트를 찾음
		scrapPost.setScrap_author(author);
		scrapPost.setAuthor(num);
		
		if(postService.scrapPost(scrapPost) == 0) {
			return "failed";
		}
		
		return "success";
	}
	
	@DeleteMapping("/post/post")
	@ApiOperation(value = "게시물 삭제하기")
<<<<<<< HEAD
	public Object deletePost(@RequestParam(required = true) int post,
							@RequestParam(required = true) int num) throws Exception {
		
		System.out.println("-----------------/post/post-----------------");
		System.out.println("post : " + post);
		System.out.println("num : " + num);
=======
	public Object deletePost(@RequestParam(required = true) int num,
							@RequestParam(required = true) int mynum) throws Exception {
		System.out.println("-----------------/post/post-----------------");;
		System.out.println("num : " + num);
		System.out.println("myNum : " + mynum);
>>>>>>> 9cf119a9e5d843d95f5b5df0997a4d5e97091c0c
		
		BasicResponse result = new BasicResponse();
		
		if(postService.deletePost(num) == 0) {
			result.data = "failed";
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		
<<<<<<< HEAD
		List<Post> list = postService.getAllPost(num);
=======
		List<Post> list = postService.getAllPost(mynum);
>>>>>>> 9cf119a9e5d843d95f5b5df0997a4d5e97091c0c
		
		if(list.size() == 0) {
			result.data = "nothing";
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		     
		result.status=true;
		result.object = list;
		System.out.println(list);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
<<<<<<< HEAD
=======
	@PatchMapping("/post/post")
	@ApiOperation(value = "게시물 수정하기")
	public String updatePost(@RequestParam(required = true) int num, 
							@RequestParam(required = true) String title,
							@RequestParam(required = true) String content,
							@RequestParam(required = true) int count_star,
							@RequestParam(required = true) String address,
							@RequestParam(required = true) String image) throws Exception {
		System.out.println("-----------------/post/post-----------------");;
		System.out.println("num : " + num);
		System.out.println("title : " + title);
		System.out.println("content : " + content);
		System.out.println("count_star : " + count_star);
		System.out.println("address : " + address);
		System.out.println("image : " + image);
		
		Post post = new Post();
		post.setNum(num);
		post.setTitle(title);
		post.setContent(content);
		post.setCount_star(count_star);
		post.setAddress(address);
		post.setImage(image);
		
		if(postService.updatePost(post) == 0) {
			return "failed";
		}
		
		return "success";
	}
	
>>>>>>> 9cf119a9e5d843d95f5b5df0997a4d5e97091c0c
}
