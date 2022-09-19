package store.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import store.entities.User;
import store.services.UserService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @PostMapping("/register-user")
    public ResponseEntity<String> registerUser(@RequestBody @Valid User user) {

        if (userService.registerUser(user)) {

            userService.userAddRole(user, "USER");

            return ResponseEntity.ok("Регистрация успешна произведена!");
        } else {
            return ResponseEntity.ok("Невозможно");
        }
    }
}