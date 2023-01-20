package com.dbd.nanal.repository;

import com.dbd.nanal.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUserName(String UserName);
    UserEntity findByEmail(String email);
    UserEntity findByUserNameAndPassword(String username, String user_password);

}
