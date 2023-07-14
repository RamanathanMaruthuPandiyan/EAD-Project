package com.eventmanagement.event_management_system.model;

import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;


public class ScheduleDTO {

    private Long id;

    private LocalDate date;

    private LocalTime time;

    @Size(max = 255)
    private String location;

    @Size(max = 255)
    private String venue;

    private Long eventDetails;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(final LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(final LocalTime time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(final String location) {
        this.location = location;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(final String venue) {
        this.venue = venue;
    }

    public Long getEventDetails() {
        return eventDetails;
    }

    public void setEventDetails(final Long eventDetails) {
        this.eventDetails = eventDetails;
    }

}
