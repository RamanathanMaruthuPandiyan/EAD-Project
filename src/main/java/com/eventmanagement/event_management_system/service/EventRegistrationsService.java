package com.eventmanagement.event_management_system.service;

import com.eventmanagement.event_management_system.domain.EventRegistrations;
import com.eventmanagement.event_management_system.domain.User;
import com.eventmanagement.event_management_system.model.EventRegistrationsDTO;
import com.eventmanagement.event_management_system.repos.EventRegistrationsRepository;
import com.eventmanagement.event_management_system.repos.UserRepository;
import com.eventmanagement.event_management_system.util.NotFoundException;
import com.eventmanagement.event_management_system.util.WebUtils;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class EventRegistrationsService {

    private final EventRegistrationsRepository eventRegistrationsRepository;
    private final UserRepository userRepository;

    public EventRegistrationsService(
            final EventRegistrationsRepository eventRegistrationsRepository,
            final UserRepository userRepository) {
        this.eventRegistrationsRepository = eventRegistrationsRepository;
        this.userRepository = userRepository;
    }

    public List<EventRegistrationsDTO> findAll() {
        final List<EventRegistrations> eventRegistrationss = eventRegistrationsRepository.findAll(Sort.by("id"));
        return eventRegistrationss.stream()
                .map(eventRegistrations -> mapToDTO(eventRegistrations, new EventRegistrationsDTO()))
                .toList();
    }

    public EventRegistrationsDTO get(final Long id) {
        return eventRegistrationsRepository.findById(id)
                .map(eventRegistrations -> mapToDTO(eventRegistrations, new EventRegistrationsDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final EventRegistrationsDTO eventRegistrationsDTO) {
        final EventRegistrations eventRegistrations = new EventRegistrations();
        mapToEntity(eventRegistrationsDTO, eventRegistrations);
        return eventRegistrationsRepository.save(eventRegistrations).getId();
    }

    public void update(final Long id, final EventRegistrationsDTO eventRegistrationsDTO) {
        final EventRegistrations eventRegistrations = eventRegistrationsRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(eventRegistrationsDTO, eventRegistrations);
        eventRegistrationsRepository.save(eventRegistrations);
    }

    public void delete(final Long id) {
        final EventRegistrations eventRegistrations = eventRegistrationsRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        userRepository.findAllByEventRegistrationss(eventRegistrations)
                .forEach(user -> user.getEventRegistrationss().remove(eventRegistrations));
        eventRegistrationsRepository.delete(eventRegistrations);
    }

    private EventRegistrationsDTO mapToDTO(final EventRegistrations eventRegistrations,
            final EventRegistrationsDTO eventRegistrationsDTO) {
        eventRegistrationsDTO.setId(eventRegistrations.getId());
        eventRegistrationsDTO.setName(eventRegistrations.getName());
        eventRegistrationsDTO.setDepartment(eventRegistrations.getDepartment());
        eventRegistrationsDTO.setEventName(eventRegistrations.getEventName());
        eventRegistrationsDTO.setPhoneNumber(eventRegistrations.getPhoneNumber());
        eventRegistrationsDTO.setYearOfStudy(eventRegistrations.getYearOfStudy());
        eventRegistrationsDTO.setTeamName(eventRegistrations.getTeamName());
        return eventRegistrationsDTO;
    }

    private EventRegistrations mapToEntity(final EventRegistrationsDTO eventRegistrationsDTO,
            final EventRegistrations eventRegistrations) {
        eventRegistrations.setName(eventRegistrationsDTO.getName());
        eventRegistrations.setDepartment(eventRegistrationsDTO.getDepartment());
        eventRegistrations.setEventName(eventRegistrationsDTO.getEventName());
        eventRegistrations.setPhoneNumber(eventRegistrationsDTO.getPhoneNumber());
        eventRegistrations.setYearOfStudy(eventRegistrationsDTO.getYearOfStudy());
        eventRegistrations.setTeamName(eventRegistrationsDTO.getTeamName());
        return eventRegistrations;
    }

    public boolean teamNameExists(final String teamName) {
        return eventRegistrationsRepository.existsByTeamNameIgnoreCase(teamName);
    }

    public String getReferencedWarning(final Long id) {
        final EventRegistrations eventRegistrations = eventRegistrationsRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final User eventRegistrationssUser = userRepository.findFirstByEventRegistrationss(eventRegistrations);
        if (eventRegistrationssUser != null) {
            return WebUtils.getMessage("eventRegistrations.user.eventRegistrationss.referenced", eventRegistrationssUser.getId());
        }
        return null;
    }

}
