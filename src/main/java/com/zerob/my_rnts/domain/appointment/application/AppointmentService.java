package com.zerob.my_rnts.domain.appointment.application;

import com.zerob.my_rnts.domain.appointment.domain.Appointment;
import com.zerob.my_rnts.domain.appointment.domain.AppointmentMember;
import com.zerob.my_rnts.domain.appointment.domain.CustomAppointmentType;
import com.zerob.my_rnts.domain.appointment.dto.AppointmentDetailsResponse;
import com.zerob.my_rnts.domain.appointment.dto.AppointmentResponse;
import com.zerob.my_rnts.domain.appointment.exception.AppointmentErrorCode;
import com.zerob.my_rnts.domain.appointment.exception.AppointmentException;
import com.zerob.my_rnts.domain.appointment.repository.AppointmentMemberRepository;
import com.zerob.my_rnts.domain.appointment.repository.AppointmentRepository;
import com.zerob.my_rnts.domain.appointment.repository.CustomAppointmentTypeRepository;
import com.zerob.my_rnts.domain.member.domain.Member;
import com.zerob.my_rnts.domain.member.exception.MemberErrorCode;
import com.zerob.my_rnts.domain.member.exception.MemberException;
import com.zerob.my_rnts.domain.member.repository.MemberRepository;
import com.zerob.my_rnts.domain.member.vo.LoginId;
import com.zerob.my_rnts.global.oauth2.userInfo.CustomIntegratedUser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class AppointmentService {

    private final MemberRepository memberRepository;
    private final AppointmentRepository appointmentRepository;
    private final AppointmentMemberRepository appointmentMemberRepository;
    private final CustomAppointmentTypeRepository customAppointmentTypeRepository;

    public AppointmentResponse create(final Appointment appointment, final CustomIntegratedUser principal) {
        Appointment newAppointment = appointment.create(principal.getMember().getId(), appointment);
        appointmentRepository.save(newAppointment);

        saveAppointmentMember(newAppointment, principal);

        // 사용자 정의 약속 유형의 ID가 있는 경우 해당 유형 이름과 함께 약속 반환
        if (appointment.getCustomAppointmentTypeId() != 0) {
            CustomAppointmentType customAppointmentType = customAppointmentTypeRepository.findById(appointment.getCustomAppointmentTypeId())
                    .orElseThrow(() -> new AppointmentException(AppointmentErrorCode.CUSTOM_TYPE_NOT_FOUND));
            return AppointmentResponse.of(newAppointment, customAppointmentType);
        }
        else
            return AppointmentResponse.of(newAppointment);
    }

    public AppointmentDetailsResponse getAppointmentDetails(final Long appointmentId) {
        Appointment appointment = getAppointment(appointmentId);

        // 초대한 사람 이름 찾기
        Member creator = memberRepository.findById(appointment.getCreatorId())
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        return AppointmentDetailsResponse.of(creator.getNickname(), appointment);
    }

    public void delete(final Long appointmentId, final CustomIntegratedUser principal) {
        appointmentMemberRepository.deleteByAppointmentIdAndMemberId(appointmentId, getMember(principal).getId());

        // 멤버가 약속에서 빠질 때 해당 약속 id가 포함된 중간테이블의 레코드가 없을 경우 해당 약속의 멤버가 없음 -> 약속 삭제
        if (appointmentMemberRepository.findAllByAppointmentId(appointmentId).isEmpty())
            appointmentRepository.deleteById(appointmentId);
    }

    public void acceptInvite(final Long appointmentId, final CustomIntegratedUser principal) {
        saveAppointmentMember(getAppointment(appointmentId), principal);
    }

    public void udpate(final Appointment updateAppointment, final Long appointmentId) {
        Appointment appointment = getAppointment(appointmentId);
        appointment.update(updateAppointment);
    }

    private void saveAppointmentMember(final Appointment appointment, final CustomIntegratedUser principal) {
        Member member = getMember(principal);

        AppointmentMember newAppointmentMember = AppointmentMember.builder()
                .appointment(appointment).member(member).build();

        appointmentMemberRepository.save(newAppointmentMember);
    }

    private Appointment getAppointment(final Long appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AppointmentException(AppointmentErrorCode.APPOINTMENT_NOT_FOUND));
    }

    private Member getMember(final CustomIntegratedUser principal) {
        return memberRepository.findByLoginId(LoginId.from(principal.getUsername()))
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
    }
}