package com.currency.teamflow.domain.user.repository;

import com.currency.teamflow.domain.user.entity.User;
import com.currency.teamflow.global.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findUserByEmailAndStatus(String email, Status status);

    boolean existsUserByEmail(String email);

}
