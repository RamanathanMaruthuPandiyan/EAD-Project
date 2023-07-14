package com.eventmanagement.event_management_system.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.Set;


@Entity
public class EventDetails {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String eventName;

    @Column(name = "\"description\"", columnDefinition = "longtext")
    private String description;

    @Column
    private Integer numberofRounds;

    @Column(columnDefinition = "longtext")
    private String roundWiseDescription;

    @Column(columnDefinition = "longtext")
    private String rules;

    @Column
    private String tagline;

    @Column
    private String teamSize;

    @OneToOne(mappedBy = "eventDetails", fetch = FetchType.LAZY)
    private User users;

    @OneToMany(mappedBy = "eventDetails")
    private Set<Schedule> schedules;

    @OneToMany(mappedBy = "eventDetails")
    private Set<Judges> judgess;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(final String eventName) {
        this.eventName = eventName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Integer getNumberofRounds() {
        return numberofRounds;
    }

    public void setNumberofRounds(final Integer numberofRounds) {
        this.numberofRounds = numberofRounds;
    }

    public String getRoundWiseDescription() {
        return roundWiseDescription;
    }

    public void setRoundWiseDescription(final String roundWiseDescription) {
        this.roundWiseDescription = roundWiseDescription;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(final String rules) {
        this.rules = rules;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(final String tagline) {
        this.tagline = tagline;
    }

    public String getTeamSize() {
        return teamSize;
    }

    public void setTeamSize(final String teamSize) {
        this.teamSize = teamSize;
    }

    public User getUsers() {
        return users;
    }

    public void setUsers(final User users) {
        this.users = users;
    }

    public Set<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(final Set<Schedule> schedules) {
        this.schedules = schedules;
    }

    public Set<Judges> getJudgess() {
        return judgess;
    }

    public void setJudgess(final Set<Judges> judgess) {
        this.judgess = judgess;
    }

}
