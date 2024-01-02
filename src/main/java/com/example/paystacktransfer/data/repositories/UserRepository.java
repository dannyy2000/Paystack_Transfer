package com.example.paystacktransfer.data.repositories;

import com.example.paystacktransfer.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByCustomerCode(String customerCode);
}
