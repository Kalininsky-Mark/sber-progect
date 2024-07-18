package controllers;

import jakarta.validation.Valid;
import data_transfer.UserDataTransfer;
import tables.User;
import lombok.AllArgsConstructor;
import services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
@AllArgsConstructor
public class AuthenticationController {

    private final UserService userService;

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new UserDataTransfer());

        return "register";
    }

    @PostMapping("/register")
    public String performRegistration(@Valid @ModelAttribute("user") UserDataTransfer userDataTransfer, BindingResult result, Model model) {
        Optional<User> user = userService.findByUsername(userDataTransfer.getUsername());

        if (user.isPresent()) {
            result.rejectValue("username", "", "There is already an account registered with the same username");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", userDataTransfer);
            return "/register";
        }

        userService.save(userDataTransfer);
        return "redirect:/register?success";
    }

}