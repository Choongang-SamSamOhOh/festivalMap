//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//import com.oracle.s202350104.service.recommand.RecommendationController;
//import com.oracle.s202350104.service.recommand.RecommendationService;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.util.Arrays;
//
//@WebMvcTest(RecommendationController.class)
//public class RecommendationControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private RecommendationService recommendationService;
//
//    @Test
//    public void testGetRecommendations() throws Exception {
//        // 서비스의 동작을 Mocking
//        when(recommendationService.recommend(any(User.class)))
//            .thenReturn(Arrays.asList(new Content("content1"), new Content("content2")));
//
//        // 요청을 실행하고 응답 상태를 검증
//        mockMvc.perform(get("/recommendations"))
//            .andExpect(status().isOk());
//    }
//}