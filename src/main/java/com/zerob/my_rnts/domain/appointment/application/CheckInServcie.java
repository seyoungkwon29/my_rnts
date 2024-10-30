package com.zerob.my_rnts.domain.appointment.application;

import com.zerob.my_rnts.domain.appointment.domain.Appointment;
import com.zerob.my_rnts.domain.appointment.dto.CheckInResponse;
import com.zerob.my_rnts.domain.appointment.exception.AppointmentErrorCode;
import com.zerob.my_rnts.domain.appointment.exception.AppointmentException;
import com.zerob.my_rnts.domain.appointment.repository.AppointmentRepository;
import com.zerob.my_rnts.domain.member.domain.Member;
import com.zerob.my_rnts.domain.member.exception.MemberErrorCode;
import com.zerob.my_rnts.domain.member.exception.MemberException;
import com.zerob.my_rnts.domain.member.repository.MemberRepository;
import com.zerob.my_rnts.domain.penalty.domain.Penalty;
import com.zerob.my_rnts.domain.penalty.exception.PenaltyErrorCode;
import com.zerob.my_rnts.domain.penalty.exception.PenaltyException;
import com.zerob.my_rnts.domain.penalty.repository.PenaltyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CheckInServcie {

    private final MemberRepository memberRepository;
    private final AppointmentRepository appointmentRepository;
    private final PenaltyRepository penaltyRepository;

    public CheckInResponse checkIn(final Long memberId, final Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AppointmentException(AppointmentErrorCode.APPOINTMENT_NOT_FOUND));

        if (isFirstToArrive(appointment))
            return handleFirstCheckIn(appointment, memberId);

        return handleRegularCheckIn(appointment);
    }

    // 1등 조건 : penaltyId가 null인 경우
    private boolean isFirstToArrive(final Appointment appointment) {
        return appointment.getPenaltyId() == null;
    }

    // 1등 체크인 핸들러
    private CheckInResponse handleFirstCheckIn(final Appointment appointment, final Long memberId) {
        Penalty newPenalty = createPenaltyForFirstMember(memberId);
        appointment.checkIn(newPenalty.getId());

        return new CheckInResponse("축하합니다! 1등으로 도착했습니다.");
    }

    // 기본 패널티 객체 생성
    private Penalty createPenaltyForFirstMember(final Long memberId) {
        Penalty newPenalty = Penalty.builder().firstMemberId(memberId).build();
        penaltyRepository.save(newPenalty);

        return newPenalty;
    }

    // 1등을 제외한 나머지 체크인
    private CheckInResponse handleRegularCheckIn(final Appointment appointment) {
        Penalty penalty = penaltyRepository.findById(appointment.getPenaltyId())
                .orElseThrow(() -> new PenaltyException(PenaltyErrorCode.PENALTY_NOT_FOUND));

        Member firstMember = memberRepository.findById(penalty.getFirstMemberId())
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        if (appointment.getAppointmentTime().isAfter(LocalDateTime.now()))
            return new CheckInResponse("도착했습니다. 1등은 " + firstMember.getNickname().nickname() + " 님 입니다.");
        else
            return new CheckInResponse("지각입니다. 1등은 " + firstMember.getNickname().nickname() + " 님 입니다.");
    }
}
