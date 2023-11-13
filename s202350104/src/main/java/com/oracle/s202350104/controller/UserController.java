package com.oracle.s202350104.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oracle.s202350104.model.CommonCodes;
import com.oracle.s202350104.model.FestivalsContent;
import com.oracle.s202350104.model.Qna;
import com.oracle.s202350104.model.Tags;
import com.oracle.s202350104.model.Users;
import com.oracle.s202350104.service.CommonCodeService;
import com.oracle.s202350104.service.Paging;
import com.oracle.s202350104.service.PagingList;
import com.oracle.s202350104.service.QnaListService;
import com.oracle.s202350104.service.TagsService;
import com.oracle.s202350104.service.user.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/user")
public class UserController {
	
	private final QnaListService qs;
	private final UserService us;
	private final CommonCodeService cs;
	private final TagsService ts;
	
	@RequestMapping(value = "user")
	public String userList() {
		return "user";
	}

	@RequestMapping(value = "myPage")
	public String myPage(Model model) {
		UUID transactionId = UUID.randomUUID();
		try {
			log.info("[{}]{}:{}",transactionId, "myPage", "start");
			int userId = us.getLoggedInId();
			log.info("userId:{}/ userRole:{}",userId,us.getLoggedInUserRole());
			Optional<Users> user = us.getUserById(userId);
			model.addAttribute("user",user);
		} catch (Exception e) {
			log.error("[{}]{}:{}",transactionId,  "myPage", e.getMessage());
		}finally { 
			log.info("[{}]{}:{}",transactionId, "myPage", "end");
		}				
		return "user/myPage/index";

	}
	
	@RequestMapping(value = "myPage/myLike")
	public String myLike() {
		return "user/myPage/myLike";
	}
	
	@RequestMapping(value = "myPage/myTag")
	public String myTag() {
		return "user/myPage/myTag";
	}

	@RequestMapping(value="bizPage")
	public String bizPage() {
		return "user/bizPage/index";
	}

	@RequestMapping(value="bizPage/content")
	public String bizContent() {
		return "user/bizPage/bizContentList";
	}

	@RequestMapping(value="bizPage/addContent")
	public String bizAddContent(Model model) {
		Tags tag = new Tags();
		
		List<CommonCodes> listCodes = cs.listCommonCode();
		List<Tags> listTags = ts.listTags(tag);
		model.addAttribute("listTags", listTags);
		model.addAttribute("listCodes", listCodes);
		
		return "user/bizPage/bizContentInsert";
	}

	@RequestMapping(value="bizPage/qna")
	public String bizQna() {
		return "user/bizPage/bizQnaList";
	}


	@RequestMapping(value = "myPage/myPost")
	public String myPost() {
		return "user/myPage/myPost";
	}

	@RequestMapping(value = "myPage/qnaDetail")
	public String qnaDetail(int user_id,int id, Model model) {
		UUID transactionId = UUID.randomUUID();
		try {
			log.info("[{}]{}:{}",transactionId, "qnaDetail", "start");
			
			Qna qna = qs.detailQna(user_id, id);
			model.addAttribute("qna", qna);
		} catch (Exception e) {
			log.error("[{}]{}:{}",transactionId,  "qnaDetail", e.getMessage());
		}finally { 
			log.info("[{}]{}:{}",transactionId, "qnaDetail", "end");
		}		
		return "user/myPage/myQnaDetail";
	}

	@RequestMapping(value = "myPage/insertQnaForm")
	public String insertQnaForm(Model model) {
		UUID transactionId = UUID.randomUUID();
		try {
			log.info("[{}]{}:{}",transactionId, "qnaInsert", "start");
		} catch (Exception e) {
			log.error("[{}]{}:{}",transactionId,  "qnaInsert", e.getMessage());
		}finally { 
			log.info("[{}]{}:{}",transactionId, "qnaInsert", "end");
		}
		return "user/myPage/myQnaInsertForm";
	}
	
	@RequestMapping(value = "myPage/insertQnaResult")
	public String insertQnaResult(Qna qna, Model model) {
		UUID transactionId = UUID.randomUUID();
		Optional<Users> user = null;
		int result = 0;
		try {
			log.info("[{}]{}:{}",transactionId, "qnaInsertResult", "start");
			int userId = us.getLoggedInId();
			qna.setUser_id(userId);
			user = us.getUserById(userId);
			result = qs.insertQna(qna);
			
		} catch (Exception e) {
			log.error("[{}]{}:{}",transactionId,  "qnaInsertResult", e.getMessage());
		}finally { 
			log.info("[{}]{}:{}",transactionId, "qnaInsertResult", "end");
		}
		if(result > 0) {
			return "redirect:qnaList";	
		}else {
			model.addAttribute("msg","등록에 실패하였습니다.");
			return "forward:insertQnaForm";
		}
	}
	
	@RequestMapping(value = "myPage/updateQnaForm")
	public String updateQnaForm(int user_id, int id, Model model) {
		UUID transactionId = UUID.randomUUID();
		
		try {
			log.info("[{}]{}:{}",transactionId, "updateQnaForm", "start");
			Qna qna = qs.selectQna(user_id, id);
			model.addAttribute("qna", qna);
		} catch (Exception e) {
			log.error("[{}]{}:{}",transactionId, "updateQnaForm", e.getMessage());
		} finally {
			log.info("[{}]{}:{}",transactionId, "updateQnaForm", "end");
		}
			return "user/myPage/myQnaUpdateForm";
	}
	
	@RequestMapping(value = "myPage/updateQnaResult")
	public String updateQnaResult(Qna qna, Model model) {
		UUID transactionId = UUID.randomUUID();
		int result = 0;
		try {
			log.info("[{}]{}:{}",transactionId, "updateQnaResult", "start");
			result = qs.updateQna(qna);
		} catch (Exception e) {
			log.error("[{}]{}:{}",transactionId, "updateQnaResult", e.getMessage());
		} finally {
			log.info("[{}]{}:{}",transactionId, "updateQnaResult", "end");	
		}	
			if(result > 0) {
				return "redirect:qnaList";	
			} else {
				model.addAttribute("user_id", qna.getUser_id());
				model.addAttribute("id", qna.getId());
				model.addAttribute("msg","등록에 실패하였습니다.");	
			return "forward:updateQnaForm";
			}
	}	
	@RequestMapping(value = "myPage/qnaList")
	public String qnaList(Qna qna , String currentPage, Model model) {
		UUID transactionId = UUID.randomUUID();
			Optional<Users> user = null;
		try {
			log.info("[{}]{}:{}",transactionId, "qnaList", "start");
			
			int userId = us.getLoggedInId();
			qna.setUser_id(userId);
			int small_code = qna.getSmall_code();
			user = us.getUserById(userId);
			int totalQnaList = qs.totalQnaList(qna);
			
			PagingList page = new PagingList(totalQnaList, currentPage);
		
			qna.setStart(page.getStart());
			qna.setEnd(page.getEnd());
			
			
			log.info("qna list"+totalQnaList);
			log.info("startPage"+page.getStartPage());
			
			List<Qna> listQnaList = qs.listQnaList(qna);
			
			model.addAttribute("small_code",small_code);
			model.addAttribute("totalQnaList",totalQnaList);
			model.addAttribute("listQnaList",listQnaList);
			model.addAttribute("page",page);
		} catch (Exception e) {
			log.error("[{}]{}:{}",transactionId,  "qnaList", e.getMessage());
		}finally { 
			log.info("[{}]{}:{}",transactionId, "qnaList", "end");
		}
		if(user.isPresent() && user.get().getSmall_code() == 2) {
			return "user/myPage/myQnaList";
		}else if (user.isPresent() && user.get().getSmall_code() == 3) {
			return "user/bizPage/bizQnaList";
		
		}else {
			return"home";
			
		}
	}	
	
	@ResponseBody
	@RequestMapping(value="/deleteQnaPro")
	public String deleteQna(Qna qna) {
		log.info("delete 실행");
		int result = qs.deleteQna(qna.getId(),qna.getUser_id());
		String resultStr = Integer.toString(result);
		return resultStr;
	}

}