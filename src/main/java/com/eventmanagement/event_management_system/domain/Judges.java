package com.eventmanagement.event_management_system.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity
public class Judges {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String collegeName;

    @Column
    private String departmentName;

    @Column
    private String judgeName;

    @Column
    private String profession;

    @Column
    private String qualification;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_details_id")
    private EventDetails eventDetails;

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

    public EventDetails getEventDetails() {
        return eventDetails;
    }

    public void setEventDetails(final EventDetails eventDetails) {
        this.eventDetails = eventDetails;
    }

}
