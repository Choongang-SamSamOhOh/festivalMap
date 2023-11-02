package com.oracle.s202350104.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oracle.s202350104.dao.ExperienceDao;
import com.oracle.s202350104.model.Experience;
import com.oracle.s202350104.model.ExperienceContent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExperienceServiceImpl implements ExperienceService {

	private final ExperienceDao ed;
	
	@Override
	public List<ExperienceContent> listExperience(ExperienceContent experience) {
		List<ExperienceContent> listExperience = ed.listExperience(experience);
		return listExperience;
	}

	@Override
	public ExperienceContent detailExperience(int content_id) {
		ExperienceContent experience = ed.detailExperience(content_id);
		return experience;
	}

	@Override
	public int totalExperience() {
		int totalExperienceCnt = ed.totalExperience();
		return totalExperienceCnt;
	}

	@Override
	public int experienceDelete(int content_id) {
		int experienceDelete = 0;
		experienceDelete = ed.experienceDelete(content_id);
		
		return experienceDelete;
	}

	@Override
	public List<ExperienceContent> deletedExperience(ExperienceContent experience) {
		List<ExperienceContent> deletedExperience = ed.deletedExperience(experience);
		return deletedExperience;
	}

	@Override
	public int totalExperience2() {
		int totalExperienceCnt = ed.totalExperience2();
		return totalExperienceCnt;
	}

}
