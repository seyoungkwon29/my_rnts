package com.zerob.my_rnts.domain.appointment.application;

import com.zerob.my_rnts.MyRntsApplication;
import com.zerob.my_rnts.domain.appointment.domain.Appointment;
import com.zerob.my_rnts.domain.appointment.dto.CheckInResponse;
import com.zerob.my_rnts.domain.appointment.exception.AppointmentErrorCode;
import com.zerob.my_rnts.domain.appointment.exception.AppointmentException;
import com.zerob.my_rnts.domain.appointment.repository.AppointmentRepository;
import com.zerob.my_rnts.domain.appointment.vo.AppointmentType;
import com.zerob.my_rnts.domain.appointment.vo.Location;
import com.zerob.my_rnts.domain.appointment.vo.Title;
import com.zerob.my_rnts.domain.penalty.domain.Penalty;
import com.zerob.my_rnts.domain.penalty.exception.PenaltyErrorCode;
import com.zerob.my_rnts.domain.penalty.exception.PenaltyException;
import com.zerob.my_rnts.domain.penalty.repository.PenaltyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class CheckInServiceTest {

    @Autowired
    private CheckInServcie checkInServcie;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PenaltyRepository penaltyRepository;

    @Test
    @DisplayName("체크인 멀티 스레드 테스트")
    public void checkInForMultiThreadTest() throws InterruptedException {
        // Given
        Appointment appointment = Appointment.builder()
                .id(1L)
                .title(Title.from("테스트 약속"))
                .appointmentType(AppointmentType.DRINK)
                .appointmentTime(LocalDateTime.now().plusHours(1))
                .location(Location.from("테스트 장소", 1.0, 2.0))
                .build();
        appointmentRepository.save(appointment);

        // 동시성을 위한 쓰레드 개수 설정
        int threadCount = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        List<Long> result = Collections.synchronizedList(new ArrayList<>());

        // When : 10명의 사용자가 동시에 체크인할 경우
        for (int i = 1; i <= threadCount; i++) {
            final Long memberId = (long) i;

            executorService.submit(() -> {
                try {
                    CheckInResponse response = checkInServcie.checkIn(memberId, appointment.getId());
                    System.out.println("Member " + memberId + " - " + response.getMessage());

                    if (response.getMessage().contains("1등")) {
                        result.add(memberId);
                    }
                } finally {
                    latch.countDown(); // 모든 쓰레드가 끝날 때까지 대기
                }
            });
        }

        // 모든 쓰레드가 끝날 때까지 대기
        latch.await();
        executorService.shutdown();

        // Then : 1등을 체크한 Penalty가 정확히 생성되었는지 확인
        Appointment updatedAppointment = appointmentRepository.findById(appointment.getId())
                .orElseThrow(() -> new AppointmentException(AppointmentErrorCode.APPOINTMENT_NOT_FOUND));

        assertNotNull(updatedAppointment.getPenaltyId()); // penaltyId가 null이 아니어야 함

        Penalty penalty = penaltyRepository.findById(updatedAppointment.getPenaltyId())
                .orElseThrow(() -> new PenaltyException(PenaltyErrorCode.PENALTY_NOT_FOUND));

        assertEquals(result.get(0), penalty.getFirstMemberId());
    }
}
