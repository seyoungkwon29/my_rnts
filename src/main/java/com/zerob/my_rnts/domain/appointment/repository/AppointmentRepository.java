package com.zerob.my_rnts.domain.appointment.repository;

import com.zerob.my_rnts.domain.appointment.domain.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Optional<Appointment> findAllByCustomAppointmentTypeId(final Long customAppointmentTypeId);

}
