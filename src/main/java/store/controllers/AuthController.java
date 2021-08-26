package store.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.modelmapper.ModelMapper;
import store.dto.UserDTO;
import store.services.UserService;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    private final ModelMapper modelMapper;

    @PostMapping("/get-current-user")
    public UserDTO getCurrentUser() {

        return modelMapper.map(userService.getCurrentUser(), UserDTO.class);
    }
}