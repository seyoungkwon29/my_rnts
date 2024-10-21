package com.zerob.my_rnts.domain.penalty.dto;

import com.zerob.my_rnts.domain.penalty.domain.Penalty;
import com.zerob.my_rnts.domain.penalty.vo.Content;
import com.zerob.my_rnts.domain.penalty.vo.PenaltyType;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public record PenaltyRequest(@Valid PenaltyType penaltyType, Content content) {

    public Penalty toEntity() {
        return Penalty.builder()
                .penaltyType(penaltyType)
                .content(content)
                .build();
    }
}
