package ru.vsu.amm.inshaker.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.amm.inshaker.dto.entire.UserDTO;
import ru.vsu.amm.inshaker.dto.simple.UserSimpleDTO;
import ru.vsu.amm.inshaker.services.user.UserService;

import javax.annotation.security.RolesAllowed;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public ResponseEntity<String> registration(HttpServletRequest request, @RequestBody @Valid UserDTO user) {
        try {
            SecurityContextHolder.getContext().setAuthentication(null);
            if (!user.getPassword().equals(user.getPasswordConfirm())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You should confirm the password");
            }
            if (userService.findByUsername(user.getUsername()) != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A user with the same username already exists");
            }
            userService.save(user.getUsername(), user.getPassword());
            request.login(user.getUsername(), user.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body("Hello, " + user.getUsername());
        } catch (ServletException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("/authenticated")
    public ResponseEntity<Map<String, Boolean>> authenticated(HttpServletRequest request) {
        Map<String, Boolean> isAuth = new HashMap<>(1);
        isAuth.put("isAuth", request.getUserPrincipal() != null);
        return ResponseEntity.ok(isAuth);
    }

    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/user")
    public ResponseEntity<UserSimpleDTO> currentUser() {
        return ResponseEntity.ok(userService.getCurrentUserDTO());
    }

    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/users")
    public ResponseEntity<List<UserSimpleDTO>> allUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

}
