package com.zerob.my_rnts.global.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @CreatedDate // JPA Auditing에서 사용되며, 엔티티가 생성될 때 생성 시간을 자동으로 기록한다.
    @Column(name = "created_time", updatable = false) // 엔티티가 생성될 때만 값이 설정된다.
    private LocalDateTime createdTime;

    @LastModifiedDate // 엔티티가 수정될 때 마지막 수정 시간을 자동으로 기록한다.
    @Column(name = "last_modified_time")
    private LocalDateTime lastModifiedTime;

    @Column(name = "deleted_time")
    private LocalDateTime deletedTime;

    @Column(name = "creator_id")
    private Long creatorId;

    public void recordDeletedTime() {
        this.deletedTime = LocalDateTime.now();
    }

    // 생성자 ID 설정 메서드
    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }
}
