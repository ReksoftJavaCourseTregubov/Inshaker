package ru.vsu.amm.inshaker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.vsu.amm.inshaker.services.user.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(HttpServletRequest request,
                               @RequestParam("username") @NotBlank String username,
                               @RequestParam("password") @NotBlank String password,
                               @RequestParam("passwordConfirm") String passwordConfirm) throws ServletException {

        if (userService.findByUsername(username) != null) {
            return "redirect:/registration?errorusername";
        }

        if (!password.equals(passwordConfirm)) {
            return "redirect:/registration?errorpassword";
        }

        userService.save(username, password);
        request.login(username, password);

        return "redirect:/secret";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/secret")
    public String secret() {
        return "secret";
    }

}
