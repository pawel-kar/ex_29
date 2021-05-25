package pl.pelikan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.pelikan.model.User;
import pl.pelikan.service.UserService;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/userlogin")
    public String goToLoginForm(Model model) {
        model.addAttribute("user",new User());
        return "userlogin";
    }

    @PostMapping("/userlogin")
    public String logIntoSystem(User user) {

        return "userlogin";
    }

    @GetMapping("/register")
    public String goToRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(User user) {
        userService.registerUser(user);
        return "userlogin";
    }
}
