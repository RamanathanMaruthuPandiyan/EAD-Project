package com.eventmanagement.event_management_system.repos;

import com.eventmanagement.event_management_system.domain.EventDetails;
import com.eventmanagement.event_management_system.domain.EventRegistrations;
import com.eventmanagement.event_management_system.domain.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByEventDetailsId(Long id);

    User findFirstByEventDetails(EventDetails eventDetails);

    List<User> findAllByEventRegistrationss(EventRegistrations eventRegistrations);

    User findFirstByEventRegistrationss(EventRegistrations eventRegistrations);

}
