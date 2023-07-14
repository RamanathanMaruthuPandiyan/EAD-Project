package com.eventmanagement.event_management_system.controller;

import com.eventmanagement.event_management_system.domain.EventDetails;
import com.eventmanagement.event_management_system.model.ScheduleDTO;
import com.eventmanagement.event_management_system.repos.EventDetailsRepository;
import com.eventmanagement.event_management_system.service.ScheduleService;
import com.eventmanagement.event_management_system.util.CustomCollectors;
import com.eventmanagement.event_management_system.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final EventDetailsRepository eventDetailsRepository;

    public ScheduleController(final ScheduleService scheduleService,
            final EventDetailsRepository eventDetailsRepository) {
        this.scheduleService = scheduleService;
        this.eventDetailsRepository = eventDetailsRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("eventDetailsValues", eventDetailsRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(EventDetails::getId, EventDetails::getEventName)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("schedules", scheduleService.findAll());
        return "schedule/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("schedule") final ScheduleDTO scheduleDTO) {
        return "schedule/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("schedule") @Valid final ScheduleDTO scheduleDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "schedule/add";
        }
        scheduleService.create(scheduleDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("schedule.create.success"));
        return "redirect:/schedules";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("schedule", scheduleService.get(id));
        return "schedule/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Long id,
            @ModelAttribute("schedule") @Valid final ScheduleDTO scheduleDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "schedule/edit";
        }
        scheduleService.update(id, scheduleDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("schedule.update.success"));
        return "redirect:/schedules";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
        scheduleService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("schedule.delete.success"));
        return "redirect:/schedules";
    }

}
