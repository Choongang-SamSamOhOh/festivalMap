package com.oracle.s202350104.service.recommand;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.oracle.s202350104.model.Contents;
import com.oracle.s202350104.model.Users;

@ExtendWith(SpringExtension.class)
public class RecommendationServiceTest {

    @Mock
    private RecommendationStrategy recommendationStrategy;

    @InjectMocks
    private RecommendationServiceImpl recommendationService;

    @Test
    public void recommendTest() {
        // Given
        Contents content = new Contents();
        Users user = new Users();
        List<ScoredContent> scoredContents = Arrays.asList(new ScoredContent(content, 1.0));
        when(recommendationStrategy.recommend(any(), any())).thenReturn(scoredContents);

        // When
        List<Contents> result = recommendationService.recommend(user, content);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(content);
    }

}