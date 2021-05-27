package pl.pelikan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.pelikan.model.User;
import pl.pelikan.service.UserService;

import java.util.List;

@RequestMapping("/adminpanel")
@Controller
public class AdminController {
    private final static String NO_DATA_AVAILABLE = "Brak danych";

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String goToAdminPanel(Model model) {
        List<User> users = userService.findAllWithoutCurrentUser();
        model.addAttribute("noData", NO_DATA_AVAILABLE);
        model.addAttribute("users", users);
        return "adminpanel";
    }

    @GetMapping("/changeUserRole/{id}")
    public String changeUserRole(@PathVariable Long id) {
        userService.changeUserRole(id);
        return "redirect:/adminpanel";
    }
}
