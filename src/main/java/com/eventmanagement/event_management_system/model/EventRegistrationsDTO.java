package com.eventmanagement.event_management_system.model;

import jakarta.validation.constraints.Size;


public class EventRegistrationsDTO {

    private Long id;

    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String department;

    @Size(max = 255)
    private String eventName;

    @Size(max = 255)
    private String phoneNumber;

    private Integer yearOfStudy;

    @Size(max = 255)
    private String teamName;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(final String department) {
        this.department = department;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(final String eventName) {
        this.eventName = eventName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(final Integer yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(final String teamName) {
        this.teamName = teamName;
    }

}
