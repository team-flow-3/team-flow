package com.currency.teamflow.domain.user.service;

import com.currency.teamflow.domain.user.dto.UserRegisterRequestDto;
import com.currency.teamflow.domain.user.dto.UserRegisterResponseDto;
import com.currency.teamflow.domain.user.entity.User;
import com.currency.teamflow.domain.user.repository.UserRepository;
import com.currency.teamflow.global.config.PasswordEncoder;
import com.currency.teamflow.global.enums.Status;
import com.currency.teamflow.global.error.errorcode.ErrorCode;
import com.currency.teamflow.global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입
     * @param requestDto
     * @return
     */
    public UserRegisterResponseDto registerUser(UserRegisterRequestDto requestDto) {

        List<User> users = userRepository.findUserByEmailAndStatus(requestDto.getEmail(), Status.DELETE);

        if(!users.isEmpty()) {
            throw new CustomException(ErrorCode.FORBIDDEN_REGISTER);
        }

        if(userRepository.existsUserByEmail(requestDto.getEmail())){
            throw new CustomException(ErrorCode.DUPLICATE_USER_ID);
        }

        // 패스워드 인코딩
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        User user = new User(requestDto, encodedPassword);

        User savedUser = userRepository.save(user);

        return new UserRegisterResponseDto(savedUser);

    }

}
