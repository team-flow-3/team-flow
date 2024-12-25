package com.currency.teamflow.domain.user.service;

import com.currency.teamflow.domain.user.dto.UserLoginRequestDto;
import com.currency.teamflow.domain.user.dto.UserPasswordRequestDto;
import com.currency.teamflow.domain.user.dto.UserRegisterRequestDto;
import com.currency.teamflow.domain.user.dto.UserRegisterResponseDto;
import com.currency.teamflow.domain.user.entity.User;
import com.currency.teamflow.domain.user.repository.UserRepository;
import com.currency.teamflow.global.config.PasswordEncoder;
import com.currency.teamflow.global.enums.Status;
import com.currency.teamflow.global.error.errorcode.ErrorCode;
import com.currency.teamflow.global.error.exception.CustomException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserRegisterResponseDto registerUser(UserRegisterRequestDto requestDto) {

        List<User> users
                = userRepository.findUserByEmailAndStatus(requestDto.getEmail(), Status.DELETE);

        if (!users.isEmpty()) {
            throw new CustomException(ErrorCode.FORBIDDEN_REGISTER);
        }

        // 이메일 중복 검사
        if(userRepository.existsUserByEmail(requestDto.getEmail())){
            throw new CustomException(ErrorCode.DUPLICATE_USER_ID);
        }

        // 패스워드 인코딩
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        User user = new User(requestDto, encodedPassword);

        User savedUser = userRepository.save(user);

        return new UserRegisterResponseDto(savedUser);
    }

    /**
     * 로그인 가능
     * @param requestDto
     * @return
     */
    public User loginUser(UserLoginRequestDto requestDto) {

        User findUser = userRepository.findUserByEmailOrElseThrow(requestDto.getEmail());

        if (findUser.getStatus().equals(Status.DELETE)) {
            throw new CustomException(ErrorCode.FORBIDDEN_LOGIN);
        }

        // 패스워드 일치 여부 검사
        if (!passwordEncoder.matches(requestDto.getPassword(), findUser.getPassword())) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_PASSWORD);
        }

        return findUser;
    }

    /**
     * 회원 탈퇴
     * @param userId
     * @param requestDto
     */
    @Transactional
    public void deleteUser(Long userId, @Valid UserPasswordRequestDto requestDto) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        if (findUser.getStatus().equals(Status.DELETE)) {
            throw new CustomException(ErrorCode.FORBIDDEN_LOGIN);
        }

        if (!passwordEncoder.matches(requestDto.getPassword(), findUser.getPassword())) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_PASSWORD);
        }

        findUser.updateDeactivatedStatus();
    }
}
