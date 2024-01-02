package com.example.paystacktransfer.data.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferRecipientRequest {
    private String type;
    private String name;
    private String account_number;
    private String bank_code;
    private String currency;








}
