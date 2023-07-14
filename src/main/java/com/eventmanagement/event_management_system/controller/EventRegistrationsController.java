package com.eventmanagement.event_management_system.controller;

import com.eventmanagement.event_management_system.model.EventRegistrationsDTO;
import com.eventmanagement.event_management_system.service.EventRegistrationsService;
import com.eventmanagement.event_management_system.util.WebUtils;
import jakarta.validation.Valid;
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
@RequestMapping("/eventRegistrationss")
public class EventRegistrationsController {

    private final EventRegistrationsService eventRegistrationsService;

    public EventRegistrationsController(final EventRegistrationsService eventRegistrationsService) {
        this.eventRegistrationsService = eventRegistrationsService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("eventRegistrationss", eventRegistrationsService.findAll());
        return "eventRegistrations/list";
    }

    @GetMapping("/add")
    public String add(
            @ModelAttribute("eventRegistrations") final EventRegistrationsDTO eventRegistrationsDTO) {
        return "eventRegistrations/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("eventRegistrations") @Valid final EventRegistrationsDTO eventRegistrationsDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (!bindingResult.hasFieldErrors("teamName") && eventRegistrationsDTO.getTeamName() != null && eventRegistrationsService.teamNameExists(eventRegistrationsDTO.getTeamName())) {
            bindingResult.rejectValue("teamName", "Exists.eventRegistrations.teamName");
        }
        if (bindingResult.hasErrors()) {
            return "eventRegistrations/add";
        }
        eventRegistrationsService.create(eventRegistrationsDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("eventRegistrations.create.success"));
        return "redirect:/eventRegistrationss";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("eventRegistrations", eventRegistrationsService.get(id));
        return "eventRegistrations/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Long id,
            @ModelAttribute("eventRegistrations") @Valid final EventRegistrationsDTO eventRegistrationsDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        final EventRegistrationsDTO currentEventRegistrationsDTO = eventRegistrationsService.get(id);
        if (!bindingResult.hasFieldErrors("teamName") && eventRegistrationsDTO.getTeamName() != null &&
                !eventRegistrationsDTO.getTeamName().equalsIgnoreCase(currentEventRegistrationsDTO.getTeamName()) &&
                eventRegistrationsService.teamNameExists(eventRegistrationsDTO.getTeamName())) {
            bindingResult.rejectValue("teamName", "Exists.eventRegistrations.teamName");
        }
        if (bindingResult.hasErrors()) {
            return "eventRegistrations/edit";
        }
        eventRegistrationsService.update(id, eventRegistrationsDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("eventRegistrations.update.success"));
        return "redirect:/eventRegistrationss";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
        final String referencedWarning = eventRegistrationsService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            eventRegistrationsService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("eventRegistrations.delete.success"));
        }
        return "redirect:/eventRegistrationss";
    }

}
