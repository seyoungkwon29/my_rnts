package com.zerob.my_rnts.domain.appointment.repository;

import com.zerob.my_rnts.domain.appointment.domain.AppointmentMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AppointmentMemberRepository extends JpaRepository<AppointmentMember, Long> {


    void deleteByAppointmentIdAndMemberId(Long appointmentId, Long memberId);

    List<AppointmentMember> findAllByAppointmentId(Long appointmentId);

    // N + 1 문제를 해결하기 위한 join fetch
    @Query("SELECT am FROM AppointmentMember am JOIN FETCH am.appointment WHERE am.member.id = :memberId")
    List<AppointmentMember> findAllWithAppointmentsByMemberId(Long memberId);
}