package com.redfin.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.redfin.util.DateTimeConverter;

import java.time.ZonedDateTime;

public class SocrataFoodTruckData {

    @JsonProperty("dayofweekstr")
    private String dayOfWeek;

    @JsonProperty("applicant")
    private String name;

    private ZonedDateTime startTime;
    private ZonedDateTime endTime;

    @JsonProperty("location")
    private String address;

    @JsonProperty("start24")
    private void setStartTime(String start24) {
        this.startTime = DateTimeConverter.convert(start24);
    }

    @JsonProperty("end24")
    private void setEndTime(String end24) {
        this.endTime = DateTimeConverter.convert(end24);
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String getName() {
        return name;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public String getAddress() {
        return address;
    }
}
