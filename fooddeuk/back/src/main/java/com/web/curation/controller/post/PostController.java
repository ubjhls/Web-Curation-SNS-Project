package com.web.curation.controller.post;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
		
		Post post = new Post(author, title, content, star, address);
		if(!image.equals("null")) {
			post.setImage(image);
		}
		
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
			list.get(i).setPicture(postService.getPicture(num));
			if(list.get(i).getType().equals("스크랩")) {
				Post temp = postService.getPost(list.get(i).getScrapnum());
				list.get(i).setScraptitle(temp.getTitle());
			}
		}
		
		System.out.println(list);
		result.status=true;
		result.object = list;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	
	@GetMapping("/post/mypost")
	@ApiOperation(value = "내가 게시한 게시물 가져오기")
	public Object getMyPost(@RequestParam(required = true) String nickname) throws Exception {
		System.out.println("-----------------/post/mypost-----------------");
		System.out.println("nickname : " + nickname);
		
		int num = userService.getNumByNickname(nickname);
		
		BasicResponse result = new BasicResponse();
		result.data="success";
		
		List<Post> list = postService.getMyPost(num);
		
		if(list.size() == 0) {
			result.data = "failed";
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setPicture(postService.getPicture(num));
			if(list.get(i).getType().equals("스크랩")) {
				Post temp = postService.getPost(list.get(i).getScrapnum());
				list.get(i).setScraptitle(temp.getTitle());
			}
		}
		
		System.out.println(list);
		result.status=true;
		result.object = list;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/post/mylikepost")
	@ApiOperation(value = "내가 좋아요 누른 게시물 가져오기")
	public Object getMyLikePost(@RequestParam(required = true) String nickname) throws Exception {
		System.out.println("-----------------/post/mylikepost-----------------");
		System.out.println("nickname : " + nickname);
		
		int num = userService.getNumByNickname(nickname);
		
		BasicResponse result = new BasicResponse();
		result.data="success";
		
		List<Post> list = postService.getMyLikePost(num);
		if(list.size() == 0) {
			result.data = "nothing";
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		
		for (int i = 0; i < list.size(); i++) {
			int postAuthor = list.get(i).getAuthor();
			String postNick = userService.getNickname(postAuthor);
			list.get(i).setNickname(postNick);
			Postlike like = new Postlike(list.get(i).getNum(), num);
			if(postlikeService.checkLike(like) != 0) {
				list.get(i).setIslike(1);
			}
			list.get(i).setPicture(postService.getPicture(postAuthor));
			if(list.get(i).getType().equals("스크랩")) {
				Post temp = postService.getPost(list.get(i).getScrapnum());
				list.get(i).setScraptitle(temp.getTitle());
			}
		}
		
		System.out.println(list);
		result.status=true;
		result.object = list;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/post/myscrappost")
	@ApiOperation(value = "내가 스크랩한 게시물 가져오기")
	public Object getMyScrapPost(@RequestParam(required = true) String nickname) throws Exception {
		System.out.println("-----------------/post/myscrappost-----------------");
		System.out.println("nickname : " + nickname);
		
		int num = userService.getNumByNickname(nickname);
		
		BasicResponse result = new BasicResponse();
		result.data="success";
		
		List<Post> list = postService.getMyScrapPost(num);
		if(list.size() == 0) {
			result.data = "nothing";
			result.object = list;
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		
		for (int i = 0; i < list.size(); i++) {
			Post temp = postService.getPost(list.get(i).getScrapnum());
			list.get(i).setScraptitle(temp.getTitle());
			list.get(i).setPicture(postService.getPicture(num));
		}
		
		System.out.println(list);
		result.status=true;
		result.object = list;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/post/mycurationpost")
	@ApiOperation(value = "내 큐레이션 지역 게시물 가져오기")
	public Object getMyCurationPost(@RequestParam(required = true) String nickname) throws Exception {
		System.out.println("-----------------/post/mycurationpost-----------------");
		System.out.println("nickname : " + nickname);
		
		int num = userService.getNumByNickname(nickname);
		
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
			for (int i = 0; i < list.size(); i++) {
				int postAuthor = list.get(i).getAuthor();
				String postNick = userService.getNickname(postAuthor);
				list.get(i).setNickname(postNick);
				Postlike like = new Postlike(list.get(i).getNum(), num);
				if(postlikeService.checkLike(like) != 0) {
					list.get(i).setIslike(1);
				}
				list.get(i).setPicture(postService.getPicture(postAuthor));
				if(list.get(i).getType().equals("스크랩")) {
					Post ptemp = postService.getPost(list.get(i).getScrapnum());
					list.get(i).setScraptitle(ptemp.getTitle());
				}
			}
			System.out.println("모든 피드 보여줌 && (공개 사용자 OR (비공개 사용자 && 팔로우))");
		} else {
			if(placeArr[1].equals("전체")) {
				// 그 지역의 모든 동네 정보 보여줌 && (공개 사용자 OR (비공개 사용자 && 팔로우))
				Curation curation = new Curation(num, placeArr[0]);
				list = postService.getMyCurationPost(curation);
				for (int i = 0; i < list.size(); i++) {
					int postAuthor = list.get(i).getAuthor();
					String postNick = userService.getNickname(postAuthor);
					list.get(i).setNickname(postNick);
					Postlike like = new Postlike(list.get(i).getNum(), num);
					if(postlikeService.checkLike(like) != 0) {
						list.get(i).setIslike(1);
					}
					list.get(i).setPicture(postService.getPicture(postAuthor));
					if(list.get(i).getType().equals("스크랩")) {
						Post ptemp = postService.getPost(list.get(i).getScrapnum());
						list.get(i).setScraptitle(ptemp.getTitle());
					}
				}
				System.out.println("그 지역의 모든 동네 정보 보여줌 && (공개 사용자 OR (비공개 사용자 && 팔로우))");
			} else {
				// 그 지역의 동네 정보 보여줌 && ((비공개 사용자 && 팔로우) OR공개 사용자 ))
				Curation curation = new Curation(num, place);
				list = postService.getMyCurationPost(curation);
				for (int i = 0; i < list.size(); i++) {
					int postAuthor = list.get(i).getAuthor();
					String postNick = userService.getNickname(postAuthor);
					list.get(i).setNickname(postNick);
					Postlike like = new Postlike(list.get(i).getNum(), num);
					if(postlikeService.checkLike(like) != 0) {
						list.get(i).setIslike(1);
					}
					list.get(i).setPicture(postService.getPicture(postAuthor));
					if(list.get(i).getType().equals("스크랩")) {
						Post ptemp = postService.getPost(list.get(i).getScrapnum());
						list.get(i).setScraptitle(ptemp.getTitle());
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
		
		if(placeArr[0].equals("없음")) {
			list = postService.getMyFollowingPost(num);
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setPicture(postService.getPicture(list.get(i).getAuthor()));
				if(list.get(i).getType().equals("스크랩")) {
					Post ptemp = postService.getPost(list.get(i).getScrapnum());
					list.get(i).setScraptitle(ptemp.getTitle());
				}
			}
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
				list.get(i).setPicture(postService.getPicture(postAuthor));
				if(list.get(i).getType().equals("스크랩")) {
					Post ptemp = postService.getPost(list.get(i).getScrapnum());
					list.get(i).setScraptitle(ptemp.getTitle());
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
					list.get(i).setPicture(postService.getPicture(postAuthor));
					if(list.get(i).getType().equals("스크랩")) {
						Post ptemp = postService.getPost(list.get(i).getScrapnum());
						list.get(i).setScraptitle(ptemp.getTitle());
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
					list.get(i).setPicture(postService.getPicture(postAuthor));
					if(list.get(i).getType().equals("스크랩")) {
						Post ptemp = postService.getPost(list.get(i).getScrapnum());
						list.get(i).setScraptitle(ptemp.getTitle());
					}
				}
				System.out.println("그 지역의 동네 정보 보여줌 && ((비공개 사용자 && 팔로우) OR공개 사용자 ))");
			}
		}
		
		if(list.size() == 0) {
			result.data = "nothing";
			result.object = list; 
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
							@RequestParam(required = true) String title,
							@RequestParam(required = true) String content) throws Exception {
		System.out.println("-----------------/post/scrap-----------------");
		System.out.println("num : " + num); // user.num
		System.out.println("postnum : " + postnum); // post.num
		
		Post scrapPost = postService.getPost(postnum); // 해당 포스트를 찾음
		
		scrapPost.setTitle(title);
		scrapPost.setContent(content);
		scrapPost.setAuthor(num);
		scrapPost.setScrapnum(postnum);
		
		if(postService.scrapPost(scrapPost) == 0) {
			return "failed";
		}
		
		return "success";
	}
	
	@DeleteMapping("/post/post")
	@ApiOperation(value = "게시물 삭제하기")
	public Object deletePost(@RequestParam(required = true) int num,
							@RequestParam(required = true) int mynum) throws Exception {
		System.out.println("-----------------/post/post-----------------");;
		System.out.println("num : " + num);
		System.out.println("myNum : " + mynum);
		
		BasicResponse result = new BasicResponse();
		
		if(postService.deletePost(num) == 0) {
			result.data = "failed";
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		
		List<Post> list = postService.getAllPost(mynum);
		
		if(list.size() == 0) {
			result.data = "nothing";
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		     
		result.status=true;
		result.object = list;
		System.out.println(list);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
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
	
}
