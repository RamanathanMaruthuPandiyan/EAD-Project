package com.eventmanagement.event_management_system.controller;

import com.eventmanagement.event_management_system.model.EventDetailsDTO;
import com.eventmanagement.event_management_system.service.EventDetailsService;
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
@RequestMapping("/eventDetailss")
public class EventDetailsController {

    private final EventDetailsService eventDetailsService;

    public EventDetailsController(final EventDetailsService eventDetailsService) {
        this.eventDetailsService = eventDetailsService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("eventDetailss", eventDetailsService.findAll());
        return "eventDetails/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("eventDetails") final EventDetailsDTO eventDetailsDTO) {
        return "eventDetails/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("eventDetails") @Valid final EventDetailsDTO eventDetailsDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "eventDetails/add";
        }
        eventDetailsService.create(eventDetailsDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("eventDetails.create.success"));
        return "redirect:/eventDetailss";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("eventDetails", eventDetailsService.get(id));
        return "eventDetails/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Long id,
            @ModelAttribute("eventDetails") @Valid final EventDetailsDTO eventDetailsDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "eventDetails/edit";
        }
        eventDetailsService.update(id, eventDetailsDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("eventDetails.update.success"));
        return "redirect:/eventDetailss";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
        final String referencedWarning = eventDetailsService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            eventDetailsService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("eventDetails.delete.success"));
        }
        return "redirect:/eventDetailss";
    }

}
