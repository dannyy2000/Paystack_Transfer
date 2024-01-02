package com.example.paystacktransfer.services;

import com.example.paystacktransfer.data.dto.request.CreateCustomerRequest;
import com.example.paystacktransfer.data.dto.request.UpdateCustomerRequest;
import com.example.paystacktransfer.data.dto.request.ValidateCustomerRequest;
import com.example.paystacktransfer.data.dto.response.AppResponse;
import com.example.paystacktransfer.data.dto.response.PaystackResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;

import java.io.IOException;

public interface UserService {

    PaystackResponse createCustomer(@Valid CreateCustomerRequest createCustomerRequest) throws IOException;

    PaystackResponse updateCustomer(String code, @Valid  UpdateCustomerRequest updateCustomerRequest);

    PaystackResponse validateCustomer(String code, @Valid ValidateCustomerRequest validateCustomerRequest);

}

