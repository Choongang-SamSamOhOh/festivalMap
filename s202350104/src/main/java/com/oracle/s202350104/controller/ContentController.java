package com.oracle.s202350104.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oracle.s202350104.model.Banner;
import com.oracle.s202350104.service.BannerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ContentController {
	
	private final BannerService bannerService;

	@RequestMapping(value = "/")
	public String home(Model model) {	
		log.info("ContentController start!");
		String bannerUrl = "";
		String title = "";

		List<Banner> bannerMain = bannerService.getFooterBanner();

		bannerUrl = bannerMain.get(0).getUrl();
		title = bannerMain.get(0).getTitle();
		log.info("ContentController mainBanner bannerUrl : {}", bannerUrl);
		log.info("ContentController mainBanner bannerUrl : {}", title);
		
		model.addAttribute("banner", bannerMain); 
		
		return "home";
	}
}
