package com.example.paystacktransfer.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String email;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String customerCode;
    private String country;
    private String account_number;
    private String bvn;
    private String bank_code;

}

