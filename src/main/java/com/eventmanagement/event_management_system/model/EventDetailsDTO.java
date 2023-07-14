package com.eventmanagement.event_management_system.model;

import jakarta.validation.constraints.Size;


public class EventDetailsDTO {

    private Long id;

    @Size(max = 255)
    private String eventName;

    private String description;

    private Integer numberofRounds;

    private String roundWiseDescription;

    private String rules;

    @Size(max = 255)
    private String tagline;

    @Size(max = 255)
    private String teamSize;

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

}
