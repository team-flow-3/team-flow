package com.currency.teamflow.domain.user.controller;

import com.currency.teamflow.domain.user.dto.UserRegisterRequestDto;
import com.currency.teamflow.domain.user.dto.UserRegisterResponseDto;
import com.currency.teamflow.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 회원가입
     * @param requestDto
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponseDto> registerUser(@Valid @RequestBody UserRegisterRequestDto requestDto) {
        UserRegisterResponseDto registerUser = userService.registerUser(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(registerUser);
    }

}
