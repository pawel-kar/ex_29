package pl.pelikan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.pelikan.model.User;
import pl.pelikan.service.UserService;

import java.security.Principal;

@Controller
public class UserController {

    private boolean justCreatedAccount;
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String goToLoginForm(Model model, @RequestParam(required = false) String error) {
        boolean errorMessage = false;

        if (error != null) {
            errorMessage = true;
        }
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("justCreatedAccount", justCreatedAccount);
        justCreatedAccount = false;
        model.addAttribute("user", new User());
        return "login";
    }


    @GetMapping("/userPanel")
    public String goToUserPanel(Model model) {
        boolean userWithAdminRole = userService.userHasAdminRole(userService.findCurrentUser());
        model.addAttribute("userWithAdminRole", userWithAdminRole);
        return "userpanel";
    }

    @GetMapping("/register")
    public String goToRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(Model model, User user) {
        userService.registerUser(user);
        model.addAttribute("justCreatedAccount", justCreatedAccount = true);
        model.addAttribute("user", new User());
        return "redirect:/userPanel";
    }

    @GetMapping("/changeCredentials")
    public String goToChangeCredentialsForm(Model model, Principal principal) {
        String username = principal.getName();
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        return "changecredentials";
    }

    @PostMapping("/changeCredentials")
    public String changeCredentials(User user) {
        userService.changeCredentials(user);
        return "userpanel";
    }
}
