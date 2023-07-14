package com.eventmanagement.event_management_system.controller;

import com.eventmanagement.event_management_system.domain.EventDetails;
import com.eventmanagement.event_management_system.model.JudgesDTO;
import com.eventmanagement.event_management_system.repos.EventDetailsRepository;
import com.eventmanagement.event_management_system.service.JudgesService;
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
@RequestMapping("/judgess")
public class JudgesController {

    private final JudgesService judgesService;
    private final EventDetailsRepository eventDetailsRepository;

    public JudgesController(final JudgesService judgesService,
            final EventDetailsRepository eventDetailsRepository) {
        this.judgesService = judgesService;
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
        model.addAttribute("judgess", judgesService.findAll());
        return "judges/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("judges") final JudgesDTO judgesDTO) {
        return "judges/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("judges") @Valid final JudgesDTO judgesDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "judges/add";
        }
        judgesService.create(judgesDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("judges.create.success"));
        return "redirect:/judgess";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("judges", judgesService.get(id));
        return "judges/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Long id,
            @ModelAttribute("judges") @Valid final JudgesDTO judgesDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "judges/edit";
        }
        judgesService.update(id, judgesDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("judges.update.success"));
        return "redirect:/judgess";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
        judgesService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("judges.delete.success"));
        return "redirect:/judgess";
    }

}
