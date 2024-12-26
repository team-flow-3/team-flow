package com.currency.teamflow.domain.user.controller;

import com.currency.teamflow.domain.user.dto.*;
import com.currency.teamflow.domain.user.entity.User;
import com.currency.teamflow.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 회원 탈퇴
     * @param requestDto
     * @param servletRequest
     * @return
     */
    @DeleteMapping
    public ResponseEntity<UserResponseDto> deleteUser (@Valid @RequestBody UserPasswordRequestDto requestDto,
                                                       HttpServletRequest servletRequest) {
        //세션이 존재하지 않으면 null로 반환
        HttpSession session = servletRequest.getSession(false);
        User loginUser = (User) session.getAttribute("user");

        userService.deleteUser(loginUser.getId(), requestDto);

        UserResponseDto loginResponseDto
                = new UserResponseDto(loginUser.getId(), loginUser.getEmail(), "탈퇴 처리 되었습니다");

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(loginResponseDto);
    }



}
