package com.example.paystacktransfer.data.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCustomerRequest {

    @NotNull(message = "field name cannot be null")
    @NotEmpty(message = "field name cannot be empty")
    private String first_name;
    @NotNull(message = "field name cannot be null")
    @NotEmpty(message = "field name cannot be empty")
    private String last_name;
    @NotNull(message = "field name cannot be null")
    @NotEmpty(message = "field name cannot be empty")
    private String phone;

}
