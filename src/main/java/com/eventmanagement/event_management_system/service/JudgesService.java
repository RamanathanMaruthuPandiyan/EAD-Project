package com.eventmanagement.event_management_system.service;

import com.eventmanagement.event_management_system.domain.EventDetails;
import com.eventmanagement.event_management_system.domain.Judges;
import com.eventmanagement.event_management_system.model.JudgesDTO;
import com.eventmanagement.event_management_system.repos.EventDetailsRepository;
import com.eventmanagement.event_management_system.repos.JudgesRepository;
import com.eventmanagement.event_management_system.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class JudgesService {

    private final JudgesRepository judgesRepository;
    private final EventDetailsRepository eventDetailsRepository;

    public JudgesService(final JudgesRepository judgesRepository,
            final EventDetailsRepository eventDetailsRepository) {
        this.judgesRepository = judgesRepository;
        this.eventDetailsRepository = eventDetailsRepository;
    }

    public List<JudgesDTO> findAll() {
        final List<Judges> judgess = judgesRepository.findAll(Sort.by("id"));
        return judgess.stream()
                .map(judges -> mapToDTO(judges, new JudgesDTO()))
                .toList();
    }

    public JudgesDTO get(final Long id) {
        return judgesRepository.findById(id)
                .map(judges -> mapToDTO(judges, new JudgesDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final JudgesDTO judgesDTO) {
        final Judges judges = new Judges();
        mapToEntity(judgesDTO, judges);
        return judgesRepository.save(judges).getId();
    }

    public void update(final Long id, final JudgesDTO judgesDTO) {
        final Judges judges = judgesRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(judgesDTO, judges);
        judgesRepository.save(judges);
    }

    public void delete(final Long id) {
        judgesRepository.deleteById(id);
    }

    private JudgesDTO mapToDTO(final Judges judges, final JudgesDTO judgesDTO) {
        judgesDTO.setId(judges.getId());
        judgesDTO.setCollegeName(judges.getCollegeName());
        judgesDTO.setDepartmentName(judges.getDepartmentName());
        judgesDTO.setJudgeName(judges.getJudgeName());
        judgesDTO.setProfession(judges.getProfession());
        judgesDTO.setQualification(judges.getQualification());
        judgesDTO.setEventDetails(judges.getEventDetails() == null ? null : judges.getEventDetails().getId());
        return judgesDTO;
    }

    private Judges mapToEntity(final JudgesDTO judgesDTO, final Judges judges) {
        judges.setCollegeName(judgesDTO.getCollegeName());
        judges.setDepartmentName(judgesDTO.getDepartmentName());
        judges.setJudgeName(judgesDTO.getJudgeName());
        judges.setProfession(judgesDTO.getProfession());
        judges.setQualification(judgesDTO.getQualification());
        final EventDetails eventDetails = judgesDTO.getEventDetails() == null ? null : eventDetailsRepository.findById(judgesDTO.getEventDetails())
                .orElseThrow(() -> new NotFoundException("eventDetails not found"));
        judges.setEventDetails(eventDetails);
        return judges;
    }

}
