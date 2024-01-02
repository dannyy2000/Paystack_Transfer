package com.example.paystacktransfer.controllers;

import com.example.paystacktransfer.data.dto.request.CreateCustomerRequest;
import com.example.paystacktransfer.data.dto.request.UpdateCustomerRequest;
import com.example.paystacktransfer.data.dto.request.ValidateCustomerRequest;
import com.example.paystacktransfer.data.dto.response.AppResponse;
import com.example.paystacktransfer.data.dto.response.PaystackResponse;
import com.example.paystacktransfer.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor

public class UserController {

    private final UserService userService;

    @PostMapping()
    public ResponseEntity<PaystackResponse> createUser(@RequestBody CreateCustomerRequest createCustomerRequest) throws IOException {
     return ResponseEntity.ok(userService.createCustomer(createCustomerRequest));
    }

    @PutMapping("/update")
    public ResponseEntity<PaystackResponse> updatedUser(@RequestParam String code, @RequestBody UpdateCustomerRequest updateCustomerRequest){
        return ResponseEntity.ok(userService.updateCustomer(code, updateCustomerRequest));
    }

    @PostMapping("/validate")
    public ResponseEntity<PaystackResponse> validateUser(@RequestParam String code, @RequestBody ValidateCustomerRequest validateCustomerRequest){
        return ResponseEntity.ok(userService.validateCustomer(code, validateCustomerRequest));
    }



}
