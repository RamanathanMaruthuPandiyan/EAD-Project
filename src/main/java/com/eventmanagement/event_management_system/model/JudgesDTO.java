package com.eventmanagement.event_management_system.model;

import jakarta.validation.constraints.Size;


public class JudgesDTO {

    private Long id;

    @Size(max = 255)
    private String collegeName;

    @Size(max = 255)
    private String departmentName;

    @Size(max = 255)
    private String judgeName;

    @Size(max = 255)
    private String profession;

    @Size(max = 255)
    private String qualification;

    private Long eventDetails;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(final String collegeName) {
        this.collegeName = collegeName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(final String departmentName) {
        this.departmentName = departmentName;
    }

    public String getJudgeName() {
        return judgeName;
    }

    public void setJudgeName(final String judgeName) {
        this.judgeName = judgeName;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(final String profession) {
        this.profession = profession;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(final String qualification) {
        this.qualification = qualification;
    }

    public Long getEventDetails() {
        return eventDetails;
    }

    public void setEventDetails(final Long eventDetails) {
        this.eventDetails = eventDetails;
    }

}
