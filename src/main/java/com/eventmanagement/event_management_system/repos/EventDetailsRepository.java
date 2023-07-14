package com.eventmanagement.event_management_system.repos;

import com.eventmanagement.event_management_system.domain.EventDetails;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EventDetailsRepository extends JpaRepository<EventDetails, Long> {
}
