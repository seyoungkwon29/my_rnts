package com.zerob.my_rnts.domain.appointment.application;

import com.zerob.my_rnts.domain.appointment.domain.Appointment;
import com.zerob.my_rnts.domain.appointment.domain.AppointmentMember;
import com.zerob.my_rnts.domain.appointment.domain.CustomAppointmentType;
import com.zerob.my_rnts.domain.appointment.dto.AppointmentResponse;
import com.zerob.my_rnts.domain.appointment.dto.AppointmentViewResponse;
import com.zerob.my_rnts.domain.appointment.exception.AppointmentErrorCode;
import com.zerob.my_rnts.domain.appointment.exception.AppointmentException;
import com.zerob.my_rnts.domain.appointment.repository.AppointmentMemberRepository;
import com.zerob.my_rnts.domain.appointment.repository.CustomAppointmentTypeRepository;
import com.zerob.my_rnts.domain.member.dto.MemberDetailResponse;
import com.zerob.my_rnts.global.oauth2.userInfo.CustomIntegratedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentViewService {

    private final AppointmentMemberRepository appointmentMemberRepository;
    private final CustomAppointmentTypeRepository customAppointmentTypeRepository;

    public List<AppointmentResponse> getAllAppointments(final CustomIntegratedUser principal) {
        return getAppointmentsByFilter(principal, appointment -> true);
    }

    public List<AppointmentResponse> getPastAppointments(final CustomIntegratedUser principal) {
        return getAppointmentsByFilter(principal,
                appointment -> appointment.getAppointmentTime().isBefore(LocalDateTime.now()));
    }

    public List<AppointmentResponse> getUpcomingAppointments(final CustomIntegratedUser principal) {
        return getAppointmentsByFilter(principal,
                appointment -> appointment.getAppointmentTime().isAfter(LocalDateTime.now()));
    }

    public List<AppointmentResponse> getAppointmentsByFilter(final CustomIntegratedUser principal,
                                                              Predicate<AppointmentResponse> filter) {
        return appointmentMemberRepository.findAllWithAppointmentsByMemberId(principal.getMember().getId())
                .stream()
                .map(AppointmentMember -> {
                    Appointment appointment = AppointmentMember.getAppointment();
                    return mapToAppointmentResponse(appointment);
                })
                .filter(filter)
                .collect(Collectors.toList());
    }

    private CustomAppointmentType getCustomAppointmentType(final Long customAppointmentId) {
        return customAppointmentTypeRepository.findById(customAppointmentId)
                .orElseThrow(() -> new AppointmentException(AppointmentErrorCode.CUSTOM_TYPE_NOT_FOUND));
    }

    private AppointmentResponse mapToAppointmentResponse(final Appointment appointment) {
        if (appointment.getCustomAppointmentTypeId() != 0L)
            return AppointmentResponse.of(appointment, getCustomAppointmentType(appointment.getCustomAppointmentTypeId()));
        else{
            return AppointmentResponse.of(appointment);
        }
    }
}
