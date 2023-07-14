package com.eventmanagement.event_management_system.service;

import com.eventmanagement.event_management_system.domain.EventDetails;
import com.eventmanagement.event_management_system.domain.Judges;
import com.eventmanagement.event_management_system.domain.Schedule;
import com.eventmanagement.event_management_system.domain.User;
import com.eventmanagement.event_management_system.model.EventDetailsDTO;
import com.eventmanagement.event_management_system.repos.EventDetailsRepository;
import com.eventmanagement.event_management_system.repos.JudgesRepository;
import com.eventmanagement.event_management_system.repos.ScheduleRepository;
import com.eventmanagement.event_management_system.repos.UserRepository;
import com.eventmanagement.event_management_system.util.NotFoundException;
import com.eventmanagement.event_management_system.util.WebUtils;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class EventDetailsService {

    private final EventDetailsRepository eventDetailsRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;
    private final JudgesRepository judgesRepository;

    public EventDetailsService(final EventDetailsRepository eventDetailsRepository,
            final UserRepository userRepository, final ScheduleRepository scheduleRepository,
            final JudgesRepository judgesRepository) {
        this.eventDetailsRepository = eventDetailsRepository;
        this.userRepository = userRepository;
        this.scheduleRepository = scheduleRepository;
        this.judgesRepository = judgesRepository;
    }

    public List<EventDetailsDTO> findAll() {
        final List<EventDetails> eventDetailss = eventDetailsRepository.findAll(Sort.by("id"));
        return eventDetailss.stream()
                .map(eventDetails -> mapToDTO(eventDetails, new EventDetailsDTO()))
                .toList();
    }

    public EventDetailsDTO get(final Long id) {
        return eventDetailsRepository.findById(id)
                .map(eventDetails -> mapToDTO(eventDetails, new EventDetailsDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final EventDetailsDTO eventDetailsDTO) {
        final EventDetails eventDetails = new EventDetails();
        mapToEntity(eventDetailsDTO, eventDetails);
        return eventDetailsRepository.save(eventDetails).getId();
    }

    public void update(final Long id, final EventDetailsDTO eventDetailsDTO) {
        final EventDetails eventDetails = eventDetailsRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(eventDetailsDTO, eventDetails);
        eventDetailsRepository.save(eventDetails);
    }

    public void delete(final Long id) {
        eventDetailsRepository.deleteById(id);
    }

    private EventDetailsDTO mapToDTO(final EventDetails eventDetails,
            final EventDetailsDTO eventDetailsDTO) {
        eventDetailsDTO.setId(eventDetails.getId());
        eventDetailsDTO.setEventName(eventDetails.getEventName());
        eventDetailsDTO.setDescription(eventDetails.getDescription());
        eventDetailsDTO.setNumberofRounds(eventDetails.getNumberofRounds());
        eventDetailsDTO.setRoundWiseDescription(eventDetails.getRoundWiseDescription());
        eventDetailsDTO.setRules(eventDetails.getRules());
        eventDetailsDTO.setTagline(eventDetails.getTagline());
        eventDetailsDTO.setTeamSize(eventDetails.getTeamSize());
        return eventDetailsDTO;
    }

    private EventDetails mapToEntity(final EventDetailsDTO eventDetailsDTO,
            final EventDetails eventDetails) {
        eventDetails.setEventName(eventDetailsDTO.getEventName());
        eventDetails.setDescription(eventDetailsDTO.getDescription());
        eventDetails.setNumberofRounds(eventDetailsDTO.getNumberofRounds());
        eventDetails.setRoundWiseDescription(eventDetailsDTO.getRoundWiseDescription());
        eventDetails.setRules(eventDetailsDTO.getRules());
        eventDetails.setTagline(eventDetailsDTO.getTagline());
        eventDetails.setTeamSize(eventDetailsDTO.getTeamSize());
        return eventDetails;
    }

    public String getReferencedWarning(final Long id) {
        final EventDetails eventDetails = eventDetailsRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final User eventDetailsUser = userRepository.findFirstByEventDetails(eventDetails);
        if (eventDetailsUser != null) {
            return WebUtils.getMessage("eventDetails.user.eventDetails.referenced", eventDetailsUser.getId());
        }
        final Schedule eventDetailsSchedule = scheduleRepository.findFirstByEventDetails(eventDetails);
        if (eventDetailsSchedule != null) {
            return WebUtils.getMessage("eventDetails.schedule.eventDetails.referenced", eventDetailsSchedule.getId());
        }
        final Judges eventDetailsJudges = judgesRepository.findFirstByEventDetails(eventDetails);
        if (eventDetailsJudges != null) {
            return WebUtils.getMessage("eventDetails.judges.eventDetails.referenced", eventDetailsJudges.getId());
        }
        return null;
    }

}
