package com.example.paystacktransfer.data.repositories;

import com.example.paystacktransfer.data.model.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipientRepository extends JpaRepository<Recipient,Long> {
}
