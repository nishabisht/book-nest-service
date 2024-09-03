package com.v1.book_nest.repository;

import com.v1.book_nest.model.UserProfileData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDataRepository extends JpaRepository<UserProfileData, Long> {

    Optional<UserProfileData> findByUserName(String username);

    Optional<UserProfileData> findByEmailId(String email);


}
