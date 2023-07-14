package com.eventmanagement.event_management_system.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalTime;


@Entity
public class Schedule {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDate date;

    @Column
    private LocalTime time;

    @Column
    private String location;

    @Column
    private String venue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_details_id")
    private EventDetails eventDetails;

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

    public EventDetails getEventDetails() {
        return eventDetails;
    }

    public void setEventDetails(final EventDetails eventDetails) {
        this.eventDetails = eventDetails;
    }

}
