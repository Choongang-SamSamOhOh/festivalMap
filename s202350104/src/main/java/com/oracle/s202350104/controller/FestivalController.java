package com.oracle.s202350104.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oracle.s202350104.model.Areas;
import com.oracle.s202350104.model.Banner;
import com.oracle.s202350104.model.Board;
import com.oracle.s202350104.model.Festivals;
import com.oracle.s202350104.model.FestivalsContent;
import com.oracle.s202350104.service.AreaService;
import com.oracle.s202350104.service.BannerService;
import com.oracle.s202350104.service.BoardService;
import com.oracle.s202350104.service.FestivalsService;
import com.oracle.s202350104.service.Paging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class FestivalController {

	private final FestivalsService fs;
	private final AreaService as;
	private final BoardService boardService;
	private final BannerService bannerService;


	@RequestMapping(value = "festival")
	public String festival(FestivalsContent festival, String currentPage, Model model) {
		
		UUID transactionId = UUID.randomUUID();
		
		try {
			log.info("[{}]{}:{}",transactionId, "festival", "start");
			
			festival.setIs_deleted("0");
			festival.setStatus("1");
			
			int totalFestivals = fs.totalFestivals(festival);
			
			Paging page = new Paging(totalFestivals, currentPage);
			festival.setStart(page.getStart());
			festival.setEnd(page.getEnd());
			
			List<FestivalsContent> listFestivals = fs.listFestivals(festival);
			
			model.addAttribute("totalFestivals", totalFestivals);
			model.addAttribute("listFestivals", listFestivals);
			model.addAttribute("page", page);
			
			log.info("festival keyword=>"+festival.getKeyword());
			log.info("festival area=>"+festival.getArea());
			log.info("festival sigungu=>"+festival.getSigungu());
			
			/*
			 * Banner Logic 구간  --> bannerHeader, bannerFooter
			 * by 엄민용
			 * */
			List<Banner> bannerHeader = bannerService.getHeaderBanner();
			List<Banner> bannerFooter = bannerService.getFooterBanner();		
			log.info("FestivalController bannerHeader : {}", bannerHeader.get(0).getTitle());
			log.info("FestivalController bannerHeader : {}", bannerHeader.get(0).getUrl());				
			
			model.addAttribute("bannerHeader", bannerHeader);
			model.addAttribute("bannerFooter", bannerFooter); 
			
		} catch (Exception e) {
			log.error("[{}]{}:{}",transactionId, "festival", e.getMessage());
		} finally {
			log.info("[{}]{}:{}",transactionId, "festival", "end");
		}		
		return "festival/festivalList";
	}
	
	@RequestMapping(value = "festival/detail")
	public String festivalDetail(Integer contentId, String currentPage, 
								 Board board, Model model) {
		UUID transactionId = UUID.randomUUID();
		
		FestivalsContent festival = null;
		
		try {
			log.info("[{}]{}:{}",transactionId, "festival/detail", "start");
			
			// 상세정보 Logic 구간
			festival = fs.detailFestivals(contentId);
			
			int result = fs.readcountUp(contentId);
			
			model.addAttribute("festival", festival);
			
			/*
			 * review page handling용
			 * by 엄민용 
			 * */
			log.info("festivalDetail contentId : {} ", contentId);
			log.info("festivalDetail currentPage : {} ", currentPage);
			
			model.addAttribute("currentPage", currentPage);
			model.addAttribute("contentId", contentId);

		} catch (Exception e) {
			log.error("[{}]{}:{}",transactionId, "festival/detail", e.getMessage());
		} finally {
			log.info("[{}]{}:{}",transactionId, "festival/detail", "end");
		}
		
		/*
		 * review Logic 구간 
		 * by 엄민용
		 * */
		log.info("FestivalController review Start!!");
		
		int bigCode = 2;
		// 분류 code 강제 지정
		int smallCode = 6;
		int userId = 1;
		int countBoard = 0;
		
		// review별 count용
		board.setCommBigCode(festival.getBig_code());
		board.setCommSmallCode(festival.getSmall_code());
		
		// debug용
		/*
		 * int commCode = board.getCommBigCode(); int commCode2 =
		 * board.getCommSmallCode(); int commCode3 = board.getId();
		 * log.info("FestivalController commCode : {}", commCode);
		 * log.info("FestivalController commCode2 : {}", commCode2);
		 * log.info("FestivalController commCode3 : {}", commCode3);
		 */
		
		try {
			// smallCode를 이용해 countBoard를 설정
			countBoard = boardService.boardCount2(board);
			
			// Paging 작업
			// Parameter board page 추가
			Paging page = new Paging(countBoard, currentPage);			
			board.setStart(page.getStart());
			board.setEnd(page.getEnd());
			board.setContent_id(contentId);
			
			List<Board> reviewAllList = boardService.getReviewAllList(board); 
			
			log.info("FestivalController reviewBoardList before board.getStart : {} ", board.getStart());
			log.info("FestivalController reviewBoardList before board.getEnd : {} ", board.getEnd());
			log.info("FestivalController reviewBoardList before board.getEnd : {} ", board.getContent_id());
			log.info("FestivalController revicewAllList size : {}", reviewAllList.size());
			log.info("FestivalController reviewBoardList after board.getStart : {} ", board.getStart());
			log.info("FestivalController reviewBoardList after board.getEnd : {} ", board.getEnd());

			if(reviewAllList.size() != 0) {
				bigCode = reviewAllList.get(0).getBig_code();
			} else {
				log.error("FestivalController review 값이 없습니다.");
			}

			log.info("FestivalController reviewBoardList totalBoard : {} ", countBoard);
			log.info("FestivalController reviewBoardList smallCode : {} ", smallCode);
			log.info("FestivalController reviewBoardList page : {} ", page);

			model.addAttribute("reviewBoard", reviewAllList);
			model.addAttribute("page", page);
			model.addAttribute("bigCode", bigCode);
			model.addAttribute("smallCode", smallCode);
			model.addAttribute("userId", userId);
			
		} catch (Exception e) {
			log.error("FestivalController reviewBoard error : {}", e.getMessage());
		} finally {
			log.info("FestivalController reviewBoard end..");
		}
		
		return "festival/festivalDetail";
	}
	
	@RequestMapping(value = "festival/recommend")
	public String festivalRecommendation() {
		return "festival/festivalRecommend";
	}
	
	@RequestMapping(value = "festival/test")
	public String festivalTest() {
		return "festival/festivalTest";
	}
	
	@RequestMapping(value = "festival/calendar")
	public String festivalCalendar() {
		return "festival/festivalCalendar";
	}
}
