package com.zerob.my_rnts.domain.appointment.repository;

import com.zerob.my_rnts.domain.appointment.domain.Appointment;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Optional<Appointment> findAllByCustomAppointmentTypeId(final Long customAppointmentTypeId);

    // Pessitic Lock
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM Appointment a WHERE a.id = :appointmentId")
    Optional<Appointment> findByIdWithLock(@Param("appointmentId") final Long appointmentId);
}