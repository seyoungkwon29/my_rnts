package com.zerob.my_rnts.domain.penalty.domain;

import com.zerob.my_rnts.domain.penalty.vo.Content;
import com.zerob.my_rnts.domain.penalty.vo.PenaltyType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Penalty {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PenaltyType penaltyType;

    private Content content;

    private Long firstMemberId;

    @Builder
    private Penalty(Long id, PenaltyType penaltyType, Content content, Long firstMemberId) {
        this.id = id;
        this.penaltyType = penaltyType;
        this.content = content;
        this.firstMemberId = firstMemberId;
    }

    // 지각 여부가 필요할까?
}
