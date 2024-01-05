package com.oracle.s202350104.service.recommand;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.oracle.s202350104.service.ContentSerivce;
import com.oracle.s202350104.service.user.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(RecommendationController.class)
class RecommendationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecommendationService recService;

    @MockBean
    private ContentSerivce contentService;

    @MockBean
    private UserService userService;
    @Test
    public void testGetRecommendations() throws Exception {

    	mockMvc.perform(get("/recommendations"));
	}

}
