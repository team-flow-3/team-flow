package com.currency.teamflow.domain.user.service;

import com.currency.teamflow.domain.user.dto.*;
import com.currency.teamflow.domain.user.entity.User;
import com.currency.teamflow.domain.user.entity.WorkspaceUser;
import com.currency.teamflow.domain.user.repository.UserRepository;
import com.currency.teamflow.domain.workspaceuser.repository.WorkspaceUserRepository;
import com.currency.teamflow.global.config.PasswordEncoder;
import com.currency.teamflow.global.enums.Role;
import com.currency.teamflow.global.enums.Status;
import com.currency.teamflow.global.error.errorcode.ErrorCode;
import com.currency.teamflow.global.error.exception.CustomException;
import com.currency.teamflow.global.util.AuthenticationScheme;
import com.currency.teamflow.global.util.JwtProvider;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final WorkspaceUserRepository workspaceUserRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       WorkspaceUserRepository workspaceUserRepository,
                       AuthenticationManager authenticationManager,
                       JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.workspaceUserRepository = workspaceUserRepository;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    public UserRegisterResponseDto registerUser(UserRegisterRequestDto requestDto) {
        // email 중복체크
        validDuplicateEmail(requestDto.getEmail());

        // 패스워드 인코딩
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        User user = new User(requestDto, encodedPassword);

        User savedUser = userRepository.save(user);

        return new UserRegisterResponseDto(savedUser);
    }

    private void validDuplicateEmail(String email) {
        final User emailUser = userRepository.findByEmail(email).orElse(null);

        if (null != emailUser) {
            // 이미 탈퇴한 email도 재가입 불가능하다.
            throw new CustomException(ErrorCode.DUPLICATE_VALUE);
        }
    }

    /**
     * 로그인 가능
     * @param requestDto
     * @return
     */
    public JwtAuthResponse loginUser(UserLoginRequestDto requestDto) {

        User findUser = userRepository.findUserByEmailOrElseThrow(requestDto.getEmail());

        // 사용자 상태 확인
        if (findUser.getStatus().equals(Status.DELETE)) {
            throw new CustomException(ErrorCode.FORBIDDEN_LOGIN);
        }

        // 패스워드 일치 여부 검사
        if (!passwordEncoder.matches(requestDto.getPassword(), findUser.getPassword())) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_PASSWORD);
        }

        // 사용자 인증 후 인증 객체를 저장
        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDto.getEmail(),
                        requestDto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 토큰 생성
        String accessToken = this.jwtProvider.generateToken(authentication);

        return new JwtAuthResponse(AuthenticationScheme.BEARER.getName(), accessToken);
    }

    /**
     * 회원 탈퇴
     * @param userId
     * @param requestDto
     */
    @Transactional
    public void deleteUser(Long userId, @Valid UserPasswordRequestDto requestDto) {
        // 사용자 조회
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        // 사용자 상태 확인
        if (findUser.getStatus().equals(Status.DELETE)) {
            throw new CustomException(ErrorCode.FORBIDDEN_LOGIN);
        }

        // 비밀번호 검증
        if (!passwordEncoder.matches(requestDto.getPassword(), findUser.getPassword())) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_PASSWORD);
        }

        // 탈퇴 상태 업데이트
        findUser.updateDeactivatedStatus();
    }

    /**
     * 유저 조회
     * @param userId 유저 ID
     * @return UserRegisterResponseDto
     */
    @Transactional(readOnly = true)
    public UserViewResponseDto findUserById(Long userId) {

        User user = userRepository.findByUserOrElseThrow(userId);

        return new UserViewResponseDto(user);
    }

    /**
     * 워크스페이스 관리자가 멤버 역할 변경
     * @param workspaceId 워크스페이스 ID
     * @param roleUpdateDto 새로운 역할 정보
     * @return 성공 메시지
     */
    @Transactional
    public void updateWorkspaceMemberRole(Long loginUserId, Long workspaceId, RoleUpdateDto roleUpdateDto) {
        WorkspaceUser targetUser = validPlocyCheck(loginUserId, workspaceId, roleUpdateDto);

        // 역할 변경
        targetUser.setRole(roleUpdateDto.getNewRole());
        workspaceUserRepository.save(targetUser);
    }

    @NotNull
    // FIXME : 메소드 이름 적절히 변경 필요
    private WorkspaceUser validPlocyCheck(Long loginUserId, Long workspaceId, RoleUpdateDto roleUpdateDto) {
        // 사용자가 해당 워크 스페이스 속해 있는 지 확인
        WorkspaceUser workspaceUser = workspaceUserRepository.findByWorkspaceIdAndUserId(workspaceId, loginUserId)
                .orElseThrow(() -> new CustomException(ErrorCode.FORBIDDEN_PERMISSION));

        // 사용자의 권한 확인.
        if (workspaceUser.getRole() != Role.WORKSPACE_ADMIN) {
            throw new CustomException(ErrorCode.FORBIDDEN_PERMISSION);
        }

        //스스로 본인 역할 변경 불가
        if(loginUserId.equals(roleUpdateDto.getMemberId())) {
            throw new CustomException(ErrorCode.DUPLICATE_VALUE);
        }

        // 대상 멤버 조회
        WorkspaceUser targetUser = workspaceUserRepository.findByWorkspaceIdAndUserId(workspaceId, roleUpdateDto.getMemberId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        // 변경하려는 역할이 허용된 역할인지 확인
        if (!isAllowedRole(roleUpdateDto.getNewRole())) {
            throw new CustomException(ErrorCode.DUPLICATE_VALUE);
        }

        // 대상 멤버가 WORKSPACE 관리자라면 변경 불가
        if (targetUser.getRole() == Role.WORKSPACE_ADMIN) {
            throw new CustomException(ErrorCode.FORBIDDEN_PERMISSION);
        }

        return targetUser;
    }

    // 허용된 역할 검증
    private boolean isAllowedRole(Role role) {
        return role == Role.BOARD_USER || role == Role.READ_ONLY;
    }
}

