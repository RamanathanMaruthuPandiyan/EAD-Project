package com.eventmanagement.event_management_system.model;

import jakarta.validation.constraints.Size;
import java.util.List;


public class UserDTO {

    private Integer id;

    @Size(max = 255)
    private String email;

    @Size(max = 255)
    private String username;

    @Size(max = 255)
    private String password;

    private Role role;

    private Long eventDetails;

    private List<Long> eventRegistrationss;

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

    public Long getEventDetails() {
        return eventDetails;
    }

    public void setEventDetails(final Long eventDetails) {
        this.eventDetails = eventDetails;
    }

    public List<Long> getEventRegistrationss() {
        return eventRegistrationss;
    }

    public void setEventRegistrationss(final List<Long> eventRegistrationss) {
        this.eventRegistrationss = eventRegistrationss;
    }

}
