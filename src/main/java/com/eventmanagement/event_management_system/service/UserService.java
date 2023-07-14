package com.eventmanagement.event_management_system.service;

import com.eventmanagement.event_management_system.domain.EventDetails;
import com.eventmanagement.event_management_system.domain.EventRegistrations;
import com.eventmanagement.event_management_system.domain.User;
import com.eventmanagement.event_management_system.model.UserDTO;
import com.eventmanagement.event_management_system.repos.EventDetailsRepository;
import com.eventmanagement.event_management_system.repos.EventRegistrationsRepository;
import com.eventmanagement.event_management_system.repos.UserRepository;
import com.eventmanagement.event_management_system.util.NotFoundException;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final EventDetailsRepository eventDetailsRepository;
    private final EventRegistrationsRepository eventRegistrationsRepository;

    public UserService(final UserRepository userRepository,
            final EventDetailsRepository eventDetailsRepository,
            final EventRegistrationsRepository eventRegistrationsRepository) {
        this.userRepository = userRepository;
        this.eventDetailsRepository = eventDetailsRepository;
        this.eventRegistrationsRepository = eventRegistrationsRepository;
    }

    public List<UserDTO> findAll() {
        final List<User> users = userRepository.findAll(Sort.by("id"));
        return users.stream()
                .map(user -> mapToDTO(user, new UserDTO()))
                .toList();
    }

    public UserDTO get(final Integer id) {
        return userRepository.findById(id)
                .map(user -> mapToDTO(user, new UserDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final UserDTO userDTO) {
        final User user = new User();
        mapToEntity(userDTO, user);
        return userRepository.save(user).getId();
    }

    public void update(final Integer id, final UserDTO userDTO) {
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(userDTO, user);
        userRepository.save(user);
    }

    public void delete(final Integer id) {
        userRepository.deleteById(id);
    }

    private UserDTO mapToDTO(final User user, final UserDTO userDTO) {
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(user.getRole());
        userDTO.setEventDetails(user.getEventDetails() == null ? null : user.getEventDetails().getId());
        userDTO.setEventRegistrationss(user.getEventRegistrationss().stream()
                .map(eventRegistrations -> eventRegistrations.getId())
                .toList());
        return userDTO;
    }

    private User mapToEntity(final UserDTO userDTO, final User user) {
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        final EventDetails eventDetails = userDTO.getEventDetails() == null ? null : eventDetailsRepository.findById(userDTO.getEventDetails())
                .orElseThrow(() -> new NotFoundException("eventDetails not found"));
        user.setEventDetails(eventDetails);
        final List<EventRegistrations> eventRegistrationss = eventRegistrationsRepository.findAllById(
                userDTO.getEventRegistrationss() == null ? Collections.emptyList() : userDTO.getEventRegistrationss());
        if (eventRegistrationss.size() != (userDTO.getEventRegistrationss() == null ? 0 : userDTO.getEventRegistrationss().size())) {
            throw new NotFoundException("one of eventRegistrationss not found");
        }
        user.setEventRegistrationss(eventRegistrationss.stream().collect(Collectors.toSet()));
        return user;
    }

    public boolean eventDetailsExists(final Long id) {
        return userRepository.existsByEventDetailsId(id);
    }

}
