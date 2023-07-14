package com.eventmanagement.event_management_system.domain;

import com.eventmanagement.event_management_system.model.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import java.util.Set;


@Entity
public class User {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String email;

    @Column
    private String username;

    @Column
    private String password;

    @Column(name = "\"role\"")
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_details_id", unique = true)
    private EventDetails eventDetails;

    @ManyToMany
    @JoinTable(
            name = "monitor_registration",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "event_registrations_id")
    )
    private Set<EventRegistrations> eventRegistrationss;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(final Role role) {
        this.role = role;
    }

    public EventDetails getEventDetails() {
        return eventDetails;
    }

    public void setEventDetails(final EventDetails eventDetails) {
        this.eventDetails = eventDetails;
    }

    public Set<EventRegistrations> getEventRegistrationss() {
        return eventRegistrationss;
    }

    public void setEventRegistrationss(final Set<EventRegistrations> eventRegistrationss) {
        this.eventRegistrationss = eventRegistrationss;
    }

}
