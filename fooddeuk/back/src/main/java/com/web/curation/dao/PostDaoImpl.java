package com.web.curation.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.web.curation.model.curation.Curation;
import com.web.curation.model.post.Post;

@Repository
public class PostDaoImpl {

	String ns="post.";
	@Autowired
	private SqlSession sqlSession;
	
	public int insertPost(Post post) {
		return sqlSession.insert(ns+"insertPost", post);
	}
	
	public List<Post> getAllPost(int num) {
		return sqlSession.selectList(ns+"getAllPost", num);
	}
	
	public int likeCountUp(int num) {
		return sqlSession.update(ns+"likeCountUp", num);
	}
	
	public int likeCountDown(int num) {
		return sqlSession.update(ns+"likeCountDown", num);
	}
	
	public int commentCountUp(int num) {
		return sqlSession.update(ns+"commentCountUp", num);
	}
	
	public int commentCountDown(int num) {
		return sqlSession.update(ns+"commentCountDown", num);
	}
	
	public int getAuthor(int num) {
		return sqlSession.selectOne(ns+"getAuthor", num);
	}
	
	public List<Post> getMyPost(int num) { // 내가 작성한 피드
		return sqlSession.selectList(ns+"getMyPost", num);
	}
	
	public List<Post> getMyLikePost(int num) { // 내가 좋아한 피드
		return sqlSession.selectList(ns+"getMyLikePost", num);
	}
	
	public List<Post> getMyCurationPost(Curation curation) { // 내 큐레이션 피드
		return sqlSession.selectList(ns+"getMyCurationPost", curation);
	}
	
	public List<Post> getAllUserPost(int num) { // 공개된 사용자 모든 피드 불러오기
		return sqlSession.selectList(ns+"getAllUserPost", num);
	}
	
	public Post getPost(Post post) {
		return sqlSession.selectOne(ns+"getPost", post);
	}
	
	public int scrapPost(Post post) {
		return sqlSession.insert(ns+"scrapPost", post);
	}
	
	public int deletePost(int num) {
		return sqlSession.delete(ns+"deletePost", num);
	}
	
	public List<Post> getMyFollowingPost(int num){
		return sqlSession.selectList(ns+"getMyFollowingPost", num);
	}
	
	public List<Post> getMyMainPost(Curation curation){
		return sqlSession.selectList(ns+"getMyMainPost", curation);
	}
	
	public int updatePost(Post post) {
		return sqlSession.update(ns+"updatePost", post);
	}
	
}
