package com.example.paystacktransfer.data;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    private String email;
    private String phone;
    private String first_name;
    private String last_name;
}
