//package com.zerob.my_rnts.domain.appointment.api;
//
//import com.zerob.my_rnts.domain.appointment.application.CheckInServcie;
//import com.zerob.my_rnts.domain.appointment.dto.CheckInResponse;
//import com.zerob.my_rnts.domain.member.WithAuthUser;
//import com.zerob.my_rnts.domain.member.domain.Member;
//import com.zerob.my_rnts.global.oauth2.userInfo.CustomIntegratedUser;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ActiveProfiles("test")
//@WebMvcTest(CheckInController.class)
//// Controller 레아어만 로드
//// MockMvc 객체 자동 주입
//public class CheckInControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private CheckInServcie checkInServcie;
//
//    @Test
//    @WithAuthUser
//    public void 체크인_테스트() throws Exception {
//        // Given : Mock 서비스의 반환값 설정
//        CheckInResponse mockResponse = new CheckInResponse("축하합니다! 1등으로 도착했습니다.");
//        Mockito.when(checkInServcie.checkIn(1L, 1L))
//                .thenReturn(mockResponse);
//
//        // MockMember 설정
//        Member mockMember = Mockito.mock(Member.class);
//        Mockito.when(mockMember.getId()).thenReturn(1L);
//
//        // CustomIntegratedUser의 멤버 정보 검증
//        CustomIntegratedUser principal = (CustomIntegratedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Member member = principal.getMember();
//
//        System.out.println(member.getRole().name());
//        // ID 검증
//        assertEquals(1L, member.getId()); // WithAuthUser에서 지정한 ID 값
//
//        // When, Then : POST 요청을 보낸 후 응답 검증
//        mockMvc.perform(post("/api/check-in/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.message").value("축하합니다! 1등으로 도착했습니다."))
//                .andDo(print());
//    }
//}
