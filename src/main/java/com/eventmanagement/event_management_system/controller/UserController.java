package com.eventmanagement.event_management_system.controller;

import com.eventmanagement.event_management_system.domain.EventDetails;
import com.eventmanagement.event_management_system.domain.EventRegistrations;
import com.eventmanagement.event_management_system.model.Role;
import com.eventmanagement.event_management_system.model.UserDTO;
import com.eventmanagement.event_management_system.repos.EventDetailsRepository;
import com.eventmanagement.event_management_system.repos.EventRegistrationsRepository;
import com.eventmanagement.event_management_system.service.UserService;
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
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final EventDetailsRepository eventDetailsRepository;
    private final EventRegistrationsRepository eventRegistrationsRepository;

    public UserController(final UserService userService,
            final EventDetailsRepository eventDetailsRepository,
            final EventRegistrationsRepository eventRegistrationsRepository) {
        this.userService = userService;
        this.eventDetailsRepository = eventDetailsRepository;
        this.eventRegistrationsRepository = eventRegistrationsRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("roleValues", Role.values());
        model.addAttribute("eventDetailsValues", eventDetailsRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(EventDetails::getId, EventDetails::getEventName)));
        model.addAttribute("eventRegistrationssValues", eventRegistrationsRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(EventRegistrations::getId, EventRegistrations::getName)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("users", userService.findAll());
        return "user/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("user") final UserDTO userDTO) {
        return "user/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("user") @Valid final UserDTO userDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (!bindingResult.hasFieldErrors("eventDetails") && userDTO.getEventDetails() != null && userService.eventDetailsExists(userDTO.getEventDetails())) {
            bindingResult.rejectValue("eventDetails", "Exists.user.eventDetails");
        }
        if (bindingResult.hasErrors()) {
            return "user/add";
        }
        userService.create(userDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("user.create.success"));
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Integer id, final Model model) {
        model.addAttribute("user", userService.get(id));
        return "user/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Integer id,
            @ModelAttribute("user") @Valid final UserDTO userDTO, final BindingResult bindingResult,
            final RedirectAttributes redirectAttributes) {
        final UserDTO currentUserDTO = userService.get(id);
        if (!bindingResult.hasFieldErrors("eventDetails") && userDTO.getEventDetails() != null &&
                !userDTO.getEventDetails().equals(currentUserDTO.getEventDetails()) &&
                userService.eventDetailsExists(userDTO.getEventDetails())) {
            bindingResult.rejectValue("eventDetails", "Exists.user.eventDetails");
        }
        if (bindingResult.hasErrors()) {
            return "user/edit";
        }
        userService.update(id, userDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("user.update.success"));
        return "redirect:/users";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Integer id,
            final RedirectAttributes redirectAttributes) {
        userService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("user.delete.success"));
        return "redirect:/users";
    }

}
