package com.currency.teamflow.domain.user.repository;

import com.currency.teamflow.domain.user.entity.User;
import com.currency.teamflow.global.enums.Status;
import com.currency.teamflow.global.error.errorcode.ErrorCode;
import com.currency.teamflow.global.error.exception.CustomException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findUserByEmailAndStatus(String email, Status status);

    boolean existsUserByEmail(String email);

}
