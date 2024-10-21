package com.zerob.my_rnts.domain.appointment.vo;

import lombok.Getter;

@Getter
public enum AppointmentType {

    DEFAULT("기타", "https://example.com/images/default.png"),
    DRINK("술", "https://example.com/images/drink.png"),
    MEAL("식사", "https://example.com/images/meal.png"),
    HOBBY("취미", "https://example.com/images/hobby.png"),
    STUDY("스터디", "https://example.com/images/team.png");

    private final String typeName;
    private final String imageUrl;

    AppointmentType(String typeName, String imageUrl) {
        this.typeName = typeName;
        this.imageUrl = imageUrl;
    }
}
