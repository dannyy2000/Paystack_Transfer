package com.example.paystacktransfer.controllers;

import com.example.paystacktransfer.data.dto.request.CreateCustomerRequest;
import com.example.paystacktransfer.data.dto.request.FinalizeTransferRequest;
import com.example.paystacktransfer.data.dto.request.InitiateTransferRequest;
import com.example.paystacktransfer.data.dto.request.TransferRecipientRequest;
import com.example.paystacktransfer.data.dto.response.PaystackResponse;
import com.example.paystacktransfer.services.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/transfer")
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;


    @PostMapping("/createRecipient")
    public ResponseEntity<PaystackResponse> createTransferRecipient(@RequestBody TransferRecipientRequest transferRecipientRequest) throws IOException {
        return ResponseEntity.ok(transferService.createTransferRecipient(transferRecipientRequest));
    }

    @PostMapping("/initiate")
    public ResponseEntity<PaystackResponse> initiateTransfer(@RequestBody InitiateTransferRequest initiateTransferRequest) throws IOException {
        return ResponseEntity.ok(transferService.initiateTransfer(initiateTransferRequest));
    }

    @PostMapping("/finalize")
    public ResponseEntity<PaystackResponse> finalizeTransfer(@RequestBody FinalizeTransferRequest finalizeTransferRequest) throws IOException {
        return ResponseEntity.ok(transferService.finalizeTransfer(finalizeTransferRequest));
    }

}
