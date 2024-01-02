package com.example.paystacktransfer.services;

import com.example.paystacktransfer.config.PaystackConfig;
import com.example.paystacktransfer.data.dto.request.FinalizeTransferRequest;
import com.example.paystacktransfer.data.dto.request.InitiateTransferRequest;
import com.example.paystacktransfer.data.dto.request.TransferRecipientRequest;
import com.example.paystacktransfer.data.dto.response.PaystackResponse;
import com.example.paystacktransfer.data.model.Recipient;
import com.example.paystacktransfer.data.repositories.RecipientRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransferServiceImpl implements TransferService{
    private final PaystackConfig paystackConfig;

    private final RecipientRepository recipientRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public PaystackResponse createTransferRecipient(TransferRecipientRequest transferRecipientRequest) {
        try{
            String url = "https://api.paystack.co/transferrecipient";

            String json = convertObjToJson(transferRecipientRequest);
            Response response = sendRequest(url,"POST",json);
            PaystackResponse paystackResponse = handleApiResponse(response);

            if(response.isSuccessful()){
                Recipient recipient = new Recipient();
                saveRecipient(recipient,transferRecipientRequest);
            }

            response.close();
            return paystackResponse;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public PaystackResponse initiateTransfer(InitiateTransferRequest initiateTransferRequest) {
        try{

            String url = "https://api.paystack.co/transfer";

            String body = convertObjToJson(initiateTransferRequest);
            Response response = sendRequest(url,"POST",body);
            PaystackResponse paystackResponse = handleApiResponse(response);

            response.close();
            return paystackResponse;


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PaystackResponse finalizeTransfer(FinalizeTransferRequest finalizeTransferRequest) {
        try{

            String url = "https://api.paystack.co/transfer/finalize_transfer";

            String body = convertObjToJson(finalizeTransferRequest);
            Response response = sendRequest(url,"POST",body);
            PaystackResponse paystackResponse = handleApiResponse(response);

            response.close();
            return paystackResponse;


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveRecipient(Recipient recipient, TransferRecipientRequest transferRecipientRequest ) {

        recipient.setName(transferRecipientRequest.getName());
        recipient.setAccount_number(transferRecipientRequest.getAccount_number());
        recipient.setBank_code(transferRecipientRequest.getBank_code());

        recipientRepository.save(recipient);
    }

    private Response sendRequest(String url, String method, String body) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody requestBody = RequestBody.create(body,mediaType);

        Request request = new Request.Builder()
                .url(url)
                .method(method,requestBody)
                .addHeader("Authorization", "Bearer " + paystackConfig.getPaystackSecretKey())
                .build();

        return okHttpClient.newCall(request).execute();
    }

    private String convertObjToJson(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    private PaystackResponse handleApiResponse(Response response) throws IOException {
        ResponseBody responseBody = response.body();

        assert responseBody != null;
        return objectMapper.readValue(responseBody.string(),PaystackResponse.class);
    }
}
