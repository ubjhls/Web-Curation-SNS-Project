package com.web.curation.controller.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.model.BasicResponse;
import com.web.curation.model.statistics.Statistics;
import com.web.curation.service.IStatisticsService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin("*")
@RestController
public class StatisticsController {
	
	@Autowired
	private IStatisticsService statsService;
	
	@GetMapping("/stats/{num}")
	@ApiOperation(value = "특정 회원의 전체 통계 가져오기")
	public Object getAllSearch(@RequestParam(required = true) int num) throws Exception {
		System.out.println("-----------------/stats/{num}-----------------");
		System.out.println("num : " + num);
		
		BasicResponse result = new BasicResponse();
		Statistics stats = new Statistics();
		stats.setNum(num);
		
		stats.setPost_count(statsService.getPostCount(num));
		stats.setScrap_count(statsService.getScrapCount(num));
		stats.setLike_count(statsService.getLikeCount(num));
		stats.setLikeClick_count(statsService.getLikeClickCount(num));
		stats.setComment_count(statsService.getCommentCount(num));
		stats.setCommentWrite_count(statsService.getCommentWriteCount(num));
		
		result.data = "success";
		result.object = stats;
		return result;
	}
	
	@GetMapping("/stats/date/{num}")
	@ApiOperation(value = "특정 회원의 월별 통계 가져오기")
	public Object getAllSearch(@RequestParam(required = true) int num,
								@RequestParam(required = true) String year,
								@RequestParam(required = true) String month) throws Exception {
		System.out.println("-----------------/stats/date/{num}-----------------");
		System.out.println("num : " + num);
		System.out.println("year : " + year);
		System.out.println("month : " + month);
		
		BasicResponse result = new BasicResponse();
		Statistics stats = new Statistics();
		stats.setNum(num);
		stats.setYear(year);
		stats.setMonth(month);
		
		stats.setPost_count(statsService.getPostCountForDate(stats));
		stats.setScrap_count(statsService.getScrapCountForDate(stats));
		stats.setLike_count(statsService.getLikeCountForDate(stats));
		stats.setLikeClick_count(statsService.getLikeClickCountForDate(stats));
		stats.setComment_count(statsService.getCommentCountForDate(stats));
		stats.setCommentWrite_count(statsService.getCommentWriteCountForDate(stats));
		
		result.data = "success";
		result.object = stats;
		
		System.out.println(stats.toString());
		return result;
	}

	
}
