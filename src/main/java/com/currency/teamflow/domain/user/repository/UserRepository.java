package com.currency.teamflow.domain.user.repository;

import com.currency.teamflow.domain.user.entity.User;
import com.currency.teamflow.global.enums.Status;
import com.currency.teamflow.global.error.errorcode.ErrorCode;
import com.currency.teamflow.global.error.exception.CustomException;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsUserByEmail(String email);

    List<User> findUserByEmailAndStatus(String email, Status status);

    Optional<User> findByEmailAndStatus(String email, Status status);

    // 아이디가 있는지 검사
    default User findUserByEmailOrElseThrow (String email) {
        return findByEmailAndStatus(email, Status.NORMAL).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND)
        );
    }

    default User findByUserOrElseThrow (Long userId) {
        return findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND)
        );
    }

    Optional<User> findByIdAndEmail(Long userId, String email);

    default User findUserByIdAndEmailOrElseThrow(Long userId, String email){
        return findByIdAndEmail(userId, email).orElseThrow(
            () -> new CustomException(ErrorCode.NOT_MATCHED)
        );
    }

    Optional<User> findByEmail(String email);
}
