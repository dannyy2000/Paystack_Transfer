package com.example.paystacktransfer.data.dto.response;

import lombok.*;

import java.io.IOException;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaystackResponse {

    private boolean status;
    private String message;
    private Map<String,Object> data;

    public PaystackResponse(IOException e) {
    }
}
