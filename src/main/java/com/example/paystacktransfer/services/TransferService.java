package com.example.paystacktransfer.services;

import com.example.paystacktransfer.data.dto.request.FinalizeTransferRequest;
import com.example.paystacktransfer.data.dto.request.InitiateTransferRequest;
import com.example.paystacktransfer.data.dto.request.TransferRecipientRequest;
import com.example.paystacktransfer.data.dto.response.PaystackResponse;
import jakarta.validation.Valid;

public interface TransferService {

    PaystackResponse createTransferRecipient(@Valid TransferRecipientRequest transferRecipientRequest);

    PaystackResponse initiateTransfer(@Valid InitiateTransferRequest initiateTransferRequest);

    PaystackResponse finalizeTransfer(@Valid FinalizeTransferRequest finalizeTransferRequest);










}
