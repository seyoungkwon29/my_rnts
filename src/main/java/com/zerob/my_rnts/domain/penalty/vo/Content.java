package com.zerob.my_rnts.domain.penalty.vo;


import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Content implements Serializable {

    @Length(max = 30)
    private String content;

    public static Content from(final String content) {
        return new Content(content);
    }

    @JsonValue
    public String title() {
        return content;
    }


}
