package com.currency.teamflow.domain.user.controller;

import com.currency.teamflow.domain.user.dto.*;
import com.currency.teamflow.domain.user.entity.User;
import com.currency.teamflow.domain.user.service.UserService;
import com.currency.teamflow.global.config.auth.UserDetailsImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> loginUser (@Valid @RequestBody UserLoginRequestDto requestDto,
                                                      HttpServletRequest request) {
        if(request.getSession(false) != null) {
            request.getSession(false).invalidate();
        }

        JwtAuthResponse jwtAuthResponse = userService.loginUser(requestDto);

        return ResponseEntity.status(HttpStatus.OK).body(jwtAuthResponse);
    }

    /**
     * 로그아웃 기능
     * @param request
     * @param response
     * @param authentication
     * @return
     * @throws UsernameNotFoundException
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response,
                                         Authentication authentication) throws UsernameNotFoundException {

        if(request.getSession(false) != null) {
            request.getSession(false).invalidate();
        }

        // 인증 정보가 있다면 로그아웃 처리.
        if (authentication != null && authentication.isAuthenticated()) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);

            return ResponseEntity.ok("로그아웃 성공.");
        }

        // 인증 정보가 없다면 인증되지 않았기 때문에 로그인 필요.
        throw new UsernameNotFoundException("로그인이 먼저 필요합니다.");
    }


    /**
     * 회원 탈퇴
     * @param requestDto
     * @return
     */
    @DeleteMapping
    public ResponseEntity<UserResponseDto> deleteUser (@Valid @RequestBody UserPasswordRequestDto requestDto,
                                                       Authentication authentication) {

        // 인증 정보 내의 유저 정보 가져오기
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User loginUser = userDetails.getUser();

        userService.deleteUser(loginUser.getId(), requestDto);

        UserResponseDto loginResponseDto
                = new UserResponseDto(loginUser.getId(), loginUser.getEmail(), "탈퇴 처리 되었습니다");

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(loginResponseDto);
    }

    /**
     * 특정 유저 조회
     * @param userId 유저 ID
     * @return UserRegisterResponseDto
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserViewResponseDto> findUserById(@PathVariable Long userId) {
        UserViewResponseDto responseDto = userService.findUserById(userId);
        return ResponseEntity.ok(responseDto);
    }

    /**
     * 워크스페이스 관리자가 멤버 역할 변경
     * @param workspaceId 워크스페이스 ID
     * @param roleUpdateDto 새로운 역할 정보
     * @return 성공 메시지
     */
    @PatchMapping("/workspace/{workspaceId}/roles")
    public ResponseEntity<String> updateWorkspaceMemberRole(
            @PathVariable Long workspaceId,
            @Valid @RequestBody RoleUpdateDto roleUpdateDto,
            Authentication authentication) {

        // 인증 정보 내의 유저 정보 가져오기
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User loginUser = userDetails.getUser();

        userService.updateWorkspaceMemberRole(loginUser.getId(), workspaceId, roleUpdateDto);

        return ResponseEntity.ok("멤버의 역할이 성공적으로 변경되었습니다.");
    }

}
