package pl.pelikan.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.pelikan.model.User;

@Controller
public class HomeController {


    @GetMapping("/")
    public String home() {
        return "index";
    }


}

