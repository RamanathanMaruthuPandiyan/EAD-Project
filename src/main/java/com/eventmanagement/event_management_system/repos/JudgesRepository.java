package com.eventmanagement.event_management_system.repos;

import com.eventmanagement.event_management_system.domain.EventDetails;
import com.eventmanagement.event_management_system.domain.Judges;
import org.springframework.data.jpa.repository.JpaRepository;


public interface JudgesRepository extends JpaRepository<Judges, Long> {

    Judges findFirstByEventDetails(EventDetails eventDetails);

}
