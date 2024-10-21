package com.zerob.my_rnts.domain.member.vo;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable @Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgreeTerms implements Serializable {

    @JsonProperty("service")
    private boolean service;

    @JsonProperty("locationService")
    private boolean locationService;

    @JsonProperty("personalInformation")
    private boolean personalInformation;

    @JsonProperty("ageLimit")
    private boolean ageLimit;

    public static AgreeTerms from(final boolean service, final boolean locationService,
                                  final boolean personalInformation, final boolean ageLimit) {
        return new AgreeTerms(service, locationService, personalInformation, ageLimit);
    }

    public void setAllAgree() {
        this.service = true;
        this.locationService = true;
        this.personalInformation = true;
        this.ageLimit = true;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AgreeTerms agreeTerms = (AgreeTerms) obj;
        return Objects.equals(this.service, agreeTerms.service) &&
                Objects.equals(this.locationService, agreeTerms.locationService) &&
                Objects.equals(this.personalInformation, agreeTerms.personalInformation) &&
                Objects.equals(this.ageLimit, agreeTerms.ageLimit);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
