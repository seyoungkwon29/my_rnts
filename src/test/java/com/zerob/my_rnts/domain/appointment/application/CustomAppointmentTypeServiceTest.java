package com.zerob.my_rnts.domain.appointment.application;

import com.zerob.my_rnts.domain.appointment.domain.CustomAppointmentType;
import com.zerob.my_rnts.domain.appointment.dto.CustomAppointmentTypeResponse;
import com.zerob.my_rnts.domain.appointment.repository.CustomAppointmentTypeRepository;
import com.zerob.my_rnts.domain.member.domain.Member;
import com.zerob.my_rnts.global.oauth2.userInfo.CustomIntegratedUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.StopWatch;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class CustomAppointmentTypeServiceTest {

    @Autowired
    public CustomAppointmentTypeService customAppointmentTypeService;

    @MockBean
    public CustomAppointmentTypeRepository customAppointmentTypeRepository;

    @Mock
    public CacheManager cacheManager;

    private Member mockMember() {
        Member member = mock(Member.class);
        when(member.getId()).thenReturn(1L); // ID를 1로 설정
        return member;
    }

    @AfterEach
    void tearDown() {
        if (cacheManager.getCache("customAppointmentTypes") != null) {
            cacheManager.getCache("customAppointmentTypes").clear();
        }
    }

    @Test
    void getCustomAppointmentTypeList() {
        // Given
        CustomAppointmentType test1 = new CustomAppointmentType(1L, mockMember(), "testType1", "Image1.png");
        CustomAppointmentType test2 = new CustomAppointmentType(2L, mockMember(), "testType2", "Image2.png");

        List<CustomAppointmentType> list = Arrays.asList(test1, test2);
        when(customAppointmentTypeRepository.findAllByMemberId(1L)).thenReturn(list);

        // Principal Mock 설정
        CustomIntegratedUser principal = mock(CustomIntegratedUser.class);
        Member member = mockMember(); // 실제 member 객체
        when(principal.getMember()).thenReturn(member);
        when(member.getId()).thenReturn(1L); // Member ID 설정

        // When - 첫 번째 호출
        List<CustomAppointmentTypeResponse> responseList1 = customAppointmentTypeService.getCustomAppointmentTypeList(principal);

        // When - 두 번째 호출 (여기서는 캐시가 사용되어야 함)
        List<CustomAppointmentTypeResponse> responseList2 = customAppointmentTypeService.getCustomAppointmentTypeList(principal);

        // Then - 메서드가 한번만 호출되었는지 확인
        assertEquals(2, responseList1.size());
        assertEquals(2, responseList2.size());
        verify(customAppointmentTypeRepository, times(1)).findAllByMemberId(1L);
    }

    @Test
    @DisplayName("캐시 활용했을 때와 안했을 때 성능 비교 테스트")
    void testPerformanceWithAndWithoutCache() {
        CustomIntegratedUser principal = new CustomIntegratedUser(mockMember());

        // 캐시 없는 상태에서의 성능 측정
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("Without Cache");
        for (int i = 0; i < 10; i++) {
            customAppointmentTypeService.getCustomAppointmentTypeList(principal);
        }
        stopWatch.stop();

        // 캐시 있는 상태에서의 성능 측정
        stopWatch.start("With Cache");
        for (int i = 0; i < 10; i++) {
            customAppointmentTypeService.getCustomAppointmentTypeList(principal);
        }
        stopWatch.stop();

        System.out.println(stopWatch.prettyPrint());
    }
}