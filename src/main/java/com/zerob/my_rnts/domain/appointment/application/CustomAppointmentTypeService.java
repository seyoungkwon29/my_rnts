package com.zerob.my_rnts.domain.appointment.application;

import com.zerob.my_rnts.domain.appointment.domain.Appointment;
import com.zerob.my_rnts.domain.appointment.domain.CustomAppointmentType;
import com.zerob.my_rnts.domain.appointment.dto.CustomAppointmentTypeResponse;
import com.zerob.my_rnts.domain.appointment.exception.AppointmentErrorCode;
import com.zerob.my_rnts.domain.appointment.exception.AppointmentException;
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
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomAppointmentTypeService {

    private final CustomAppointmentTypeRepository customAppointmentTypeRepository;
    private final MemberRepository memberRepository;
    private final AppointmentRepository appointmentRepository;

    public CustomAppointmentTypeResponse create(final CustomAppointmentType customAppointmentType, final CustomIntegratedUser principal) {
        // 사용자 : 사용자 정의 약속 유형 매핑
        customAppointmentType.addMember(getMember(principal));

        customAppointmentTypeRepository.save(customAppointmentType);

        return CustomAppointmentTypeResponse.of(customAppointmentType);
    }

    @Cacheable(value = "customAppointmentTypes", key = "#principal.member.id")
    public List<CustomAppointmentTypeResponse> getCustomAppointmentTypeList(final CustomIntegratedUser principal) {
        return customAppointmentTypeRepository.findAllByMemberId(principal.getMember().getId())
                .stream()
                .map(this::mapToCustomAppointmentTypeResponse)
                .collect(Collectors.toList());
    }

    public CustomAppointmentTypeResponse update(final CustomAppointmentType newCustomAppointmentType, final Long customAppointmentTypeId) {
        CustomAppointmentType customAppointmentType = getCustomAppointmentType(customAppointmentTypeId);
        customAppointmentType.update(newCustomAppointmentType);

        return CustomAppointmentTypeResponse.of(customAppointmentType);
    }

    public void delete(final Long customAppointmentTypeId) {
        // 사용자 정의 약속 유형 삭제
        if (getCustomAppointmentType(customAppointmentTypeId) != null) {
            customAppointmentTypeRepository.deleteById(customAppointmentTypeId);
        }

        // 삭제한 사용자 정의 약속 유형을 사용한 약속 처리
        // customAppointmentId -> null, appointmentType -> DEFAULT
        Optional<Appointment> appointments = appointmentRepository.findAllByCustomAppointmentTypeId(customAppointmentTypeId);
        appointments.ifPresent(Appointment::deletedCustomAppointmentType);
    }

    public Member getMember(final CustomIntegratedUser principal) {
        return memberRepository.findByLoginId(LoginId.from(principal.getUsername()))
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
    }

    public CustomAppointmentType getCustomAppointmentType(final Long customAppointmentTypeId) {
        return customAppointmentTypeRepository.findById(customAppointmentTypeId)
                .orElseThrow(() -> new AppointmentException(AppointmentErrorCode.CUSTOM_TYPE_NOT_FOUND));
    }

    private CustomAppointmentTypeResponse mapToCustomAppointmentTypeResponse(final CustomAppointmentType customAppointmentType) {
        return CustomAppointmentTypeResponse.of(customAppointmentType);
    }
}
