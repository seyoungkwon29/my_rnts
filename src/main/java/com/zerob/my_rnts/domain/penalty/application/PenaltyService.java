package com.zerob.my_rnts.domain.penalty.application;

import com.zerob.my_rnts.domain.penalty.repository.PenaltyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PenaltyService {

    private final PenaltyRepository penaltyRepository;

}
