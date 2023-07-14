package com.eventmanagement.event_management_system.repos;

import com.eventmanagement.event_management_system.domain.EventDetails;
import com.eventmanagement.event_management_system.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    Schedule findFirstByEventDetails(EventDetails eventDetails);

}
