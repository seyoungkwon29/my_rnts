package com.zerob.my_rnts.domain.appointment.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Location implements Serializable {

    @NotBlank(message = "장소를 입력하세요.")
    @Column(nullable = false)
    @JsonProperty("place")
    private String place;

    @Column(nullable = false)
    @JsonProperty("latitude")
    private Double latitude;

    @Column(nullable = false)
    @JsonProperty("longitude")
    private Double longitude;

    public static Location from(final String place, final Double latitude, final Double longitude) {
        return new Location(place, latitude, longitude);
    }
}
