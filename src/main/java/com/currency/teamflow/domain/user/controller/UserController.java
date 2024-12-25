package com.currency.teamflow.domain.user.controller;

import com.currency.teamflow.domain.user.dto.UserLoginRequestDto;
import com.currency.teamflow.domain.user.dto.UserRegisterRequestDto;
import com.currency.teamflow.domain.user.dto.UserRegisterResponseDto;
import com.currency.teamflow.domain.user.dto.UserResponseDto;
import com.currency.teamflow.domain.user.entity.User;
import com.currency.teamflow.domain.user.repository.UserRepository;
import com.currency.teamflow.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {

        this.userService = userService;
    }

    /**
     * 회원가입
     * @param requestDto
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponseDto> registerUser (@Valid @RequestBody UserRegisterRequestDto requestDto) {
        UserRegisterResponseDto registerUser = userService.registerUser(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(registerUser);
    }

    /**
     * 로그인 기능
     * @param requestDto
     * @param servletRequest
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> loginUser (@Valid @RequestBody UserLoginRequestDto requestDto,
                                                      HttpServletRequest servletRequest) {
        User user = userService.loginUser(requestDto);
        HttpSession session = servletRequest.getSession();
        session.setAttribute("user", user);

        UserResponseDto loginResponseDto
                = new UserResponseDto(user.getId(), user.getEmail(), "로그인되었습니다.");

        return ResponseEntity.status(HttpStatus.CREATED).body(loginResponseDto);
    }

}
