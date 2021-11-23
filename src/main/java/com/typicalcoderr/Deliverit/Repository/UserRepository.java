package com.typicalcoderr.Deliverit.Repository;

import com.typicalcoderr.Deliverit.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findUserByEmail(String email);
    List <User> findUserByUserRole(String userRole);
}
