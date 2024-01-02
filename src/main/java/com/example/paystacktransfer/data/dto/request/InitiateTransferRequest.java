package com.example.paystacktransfer.data.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InitiateTransferRequest {

    private String source;
    private String amount;
    private String recipient;

}
