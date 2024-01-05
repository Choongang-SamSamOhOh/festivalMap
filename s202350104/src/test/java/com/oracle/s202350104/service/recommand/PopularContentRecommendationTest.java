package com.oracle.s202350104.service.recommand;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.oracle.s202350104.model.Contents;
import com.oracle.s202350104.model.Users;

@ExtendWith(SpringExtension.class)
public class PopularContentRecommendationTest {

    @Mock
    private RecommendationDao recommendationDao;

    @InjectMocks
    private PopularContentRecommendation recommendation;

    @Test
    public void testRecommend() {
        // 테스트 데이터 준비
        Contents content = new Contents();
        content.setReadcount(10);
        content.setAvg_score(4.5);
        content.setReview_count(5);
        List<Contents> contentList = Arrays.asList(content);

        // Mock 객체 동작 설정
        when(recommendationDao.getPopularContentsList(any())).thenReturn(contentList);

        // 테스트 실행
        Users user = new Users();
        List<ScoredContent> result = recommendation.recommend(user, new Contents());

        // 결과 검증
        assertEquals(1, result.size());
        ScoredContent scoredContent = result.get(0);
        assertEquals(content, scoredContent.getContent());
        assertEquals(10 * 0.5 + 4.5 * 0.3 + 5 * 0.2, scoredContent.getScore(), 0.01);
    }
}
