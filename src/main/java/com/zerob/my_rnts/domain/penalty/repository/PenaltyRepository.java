package com.zerob.my_rnts.domain.penalty.repository;

import com.zerob.my_rnts.domain.penalty.domain.Penalty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PenaltyRepository extends JpaRepository<Penalty, Long> {
}
