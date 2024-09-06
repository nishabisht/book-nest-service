package com.v1.book_nest.repository;

import com.v1.book_nest.model.UserProfileData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDataRepository extends JpaRepository<UserProfileData, Long>{

    Optional<UserProfileData> findByUsername(String username);

    Optional<UserProfileData> findByEmailId(String email);

}
