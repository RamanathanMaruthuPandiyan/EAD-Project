package com.eventmanagement.event_management_system.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import java.util.Set;


@Entity
public class EventRegistrations {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String department;

    @Column
    private String eventName;

    @Column
    private String phoneNumber;

    @Column
    private Integer yearOfStudy;

    @Column(unique = true)
    private String teamName;

    @ManyToMany(mappedBy = "eventRegistrationss")
    private Set<User> users;

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

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(final Set<User> users) {
        this.users = users;
    }

}
