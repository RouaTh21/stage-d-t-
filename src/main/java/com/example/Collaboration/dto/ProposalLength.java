package com.example.Collaboration.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

public class ProposalLength {

    @NotNull
    @Max(7)
    private Integer days;

    @NotNull
    @Max(23)
    private Integer hours;

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }
}
