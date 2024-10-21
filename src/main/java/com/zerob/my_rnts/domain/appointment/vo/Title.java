package com.zerob.my_rnts.domain.appointment.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Title implements Serializable {

    @NotBlank(message = "약속 제목을 입력하세요.")
    @Length(min = 2, max = 30)
    private String title;

    public static Title from(final String title) {
        return new Title(title);
    }

    @JsonValue
    public String title() {
        return title;
    }
}
