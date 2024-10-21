package com.zerob.my_rnts.domain.appointment.dto;

import com.zerob.my_rnts.domain.appointment.domain.Appointment;
import com.zerob.my_rnts.domain.appointment.domain.CustomAppointmentType;
import com.zerob.my_rnts.domain.appointment.vo.Title;
import com.zerob.my_rnts.domain.member.domain.Member;
import com.zerob.my_rnts.domain.member.dto.MemberDetailResponse;
import com.zerob.my_rnts.domain.member.vo.Nickname;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AppointmentViewResponse {

    private Long id;
    private Title title;
    private String appointmentType;
    private String customAppointmentTypeName;
    private String place;
    private LocalDateTime appointmentTime;

    private List<Nickname> nicknames; // 멤버의 닉네임 리스트
    private List<String> profileImages; // 멤버의 프로필 이미지 리스트

    public static AppointmentViewResponse of(final Appointment appointment, final List<MemberDetailResponse> responses) {
        List<Nickname> nicknames = responses.stream()
                .map(MemberDetailResponse::getNickname)
                .toList();

        List<String> profileImages = responses.stream()
                .map(MemberDetailResponse::getProfileImage)
                .toList();

        return new AppointmentViewResponse(
                appointment.getId(),
                appointment.getTitle(),
                appointment.getAppointmentType().name(),
                null,
                appointment.getLocation().getPlace(),
                appointment.getAppointmentTime(),

                nicknames,
                profileImages
        );
    }

    public static AppointmentViewResponse of(final Appointment appointment,
                                             final List<MemberDetailResponse> responses,
                                             final CustomAppointmentType customAppointmentType) {

        List<Nickname> nicknames = responses.stream()
                .map(MemberDetailResponse::getNickname)
                .toList();

        List<String> profileImages = responses.stream()
                .map(MemberDetailResponse::getProfileImage)
                .toList();

        return new AppointmentViewResponse(
                appointment.getId(),
                appointment.getTitle(),
                null,
                customAppointmentType.getTypeName(),
                appointment.getLocation().getPlace(),
                appointment.getAppointmentTime(),

                nicknames,
                profileImages
        );
    }
}
