package com.eventmanagement.event_management_system.repos;

import com.eventmanagement.event_management_system.domain.EventRegistrations;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EventRegistrationsRepository extends JpaRepository<EventRegistrations, Long> {

    boolean existsByTeamNameIgnoreCase(String teamName);

}
