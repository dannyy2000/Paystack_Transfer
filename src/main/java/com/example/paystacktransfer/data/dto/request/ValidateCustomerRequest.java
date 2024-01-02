package com.example.paystacktransfer.data.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ValidateCustomerRequest {
    private String first_name;
    private String last_name;
    private String type;
    @Pattern(regexp = "^[A-Z]{2}$", message = "Country code should be two uppercase letters")
    private String country;
    private String bank_code;
    private String bvn;
    @Size(min = 10,max = 10)
    private String account_number;


}
