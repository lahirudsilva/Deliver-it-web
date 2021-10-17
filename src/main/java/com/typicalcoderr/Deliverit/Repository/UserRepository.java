package com.typicalcoderr.Deliverit.Repository;

import com.typicalcoderr.Deliverit.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
