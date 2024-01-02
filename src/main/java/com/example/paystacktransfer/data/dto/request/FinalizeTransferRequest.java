package com.example.paystacktransfer.data.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FinalizeTransferRequest {

    private String transfer_code;
    private String otp;

}

