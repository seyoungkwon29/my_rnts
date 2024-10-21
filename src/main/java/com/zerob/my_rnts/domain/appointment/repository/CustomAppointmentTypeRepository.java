package com.zerob.my_rnts.domain.appointment.repository;

import com.zerob.my_rnts.domain.appointment.domain.CustomAppointmentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomAppointmentTypeRepository extends JpaRepository<CustomAppointmentType, Long> {
    
    List<CustomAppointmentType> findAllByMemberId(Long memberId);

}
