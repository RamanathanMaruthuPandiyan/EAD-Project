package com.eventmanagement.event_management_system.service;

import com.eventmanagement.event_management_system.domain.EventDetails;
import com.eventmanagement.event_management_system.domain.Schedule;
import com.eventmanagement.event_management_system.model.ScheduleDTO;
import com.eventmanagement.event_management_system.repos.EventDetailsRepository;
import com.eventmanagement.event_management_system.repos.ScheduleRepository;
import com.eventmanagement.event_management_system.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final EventDetailsRepository eventDetailsRepository;

    public ScheduleService(final ScheduleRepository scheduleRepository,
            final EventDetailsRepository eventDetailsRepository) {
        this.scheduleRepository = scheduleRepository;
        this.eventDetailsRepository = eventDetailsRepository;
    }

    public List<ScheduleDTO> findAll() {
        final List<Schedule> schedules = scheduleRepository.findAll(Sort.by("id"));
        return schedules.stream()
                .map(schedule -> mapToDTO(schedule, new ScheduleDTO()))
                .toList();
    }

    public ScheduleDTO get(final Long id) {
        return scheduleRepository.findById(id)
                .map(schedule -> mapToDTO(schedule, new ScheduleDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ScheduleDTO scheduleDTO) {
        final Schedule schedule = new Schedule();
        mapToEntity(scheduleDTO, schedule);
        return scheduleRepository.save(schedule).getId();
    }

    public void update(final Long id, final ScheduleDTO scheduleDTO) {
        final Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(scheduleDTO, schedule);
        scheduleRepository.save(schedule);
    }

    public void delete(final Long id) {
        scheduleRepository.deleteById(id);
    }

    private ScheduleDTO mapToDTO(final Schedule schedule, final ScheduleDTO scheduleDTO) {
        scheduleDTO.setId(schedule.getId());
        scheduleDTO.setDate(schedule.getDate());
        scheduleDTO.setTime(schedule.getTime());
        scheduleDTO.setLocation(schedule.getLocation());
        scheduleDTO.setVenue(schedule.getVenue());
        scheduleDTO.setEventDetails(schedule.getEventDetails() == null ? null : schedule.getEventDetails().getId());
        return scheduleDTO;
    }

    private Schedule mapToEntity(final ScheduleDTO scheduleDTO, final Schedule schedule) {
        schedule.setDate(scheduleDTO.getDate());
        schedule.setTime(scheduleDTO.getTime());
        schedule.setLocation(scheduleDTO.getLocation());
        schedule.setVenue(scheduleDTO.getVenue());
        final EventDetails eventDetails = scheduleDTO.getEventDetails() == null ? null : eventDetailsRepository.findById(scheduleDTO.getEventDetails())
                .orElseThrow(() -> new NotFoundException("eventDetails not found"));
        schedule.setEventDetails(eventDetails);
        return schedule;
    }

}
