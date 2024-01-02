package com.example.paystacktransfer.services;

import com.example.paystacktransfer.config.PaystackConfig;
import com.example.paystacktransfer.data.Customer;
import com.example.paystacktransfer.data.dto.request.CreateCustomerRequest;
import com.example.paystacktransfer.data.dto.request.UpdateCustomerRequest;
import com.example.paystacktransfer.data.dto.request.ValidateCustomerRequest;
import com.example.paystacktransfer.data.dto.response.AppResponse;
import com.example.paystacktransfer.data.dto.response.PaystackResponse;
import com.example.paystacktransfer.data.model.User;
import com.example.paystacktransfer.data.repositories.UserRepository;
import com.example.paystacktransfer.exception.UserException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{

    private final PaystackConfig paystackConfig;
    private  final UserRepository userRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public PaystackResponse createCustomer(CreateCustomerRequest createCustomerRequest) throws IOException {
        try {

            String url = "https://api.paystack.co/customer";

            Customer customer = new Customer();
            customer.setEmail(createCustomerRequest.getEmail());


            String jsonString = convertObjToJson(customer);
            Response response = sendRequest(url,"POST",jsonString);
            PaystackResponse paystackResponse = handleApiResponse(response);


            if(response.isSuccessful()) {
                User user = new User();
              saveUser(user,customer,paystackResponse);
            }

            response.close();
            return paystackResponse;
        }catch (IOException e){
            return new PaystackResponse(e);
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public PaystackResponse updateCustomer(String code, @Valid UpdateCustomerRequest updateCustomerRequest) {
        try{
            Optional<User> user = userRepository.findByCustomerCode(code);
            if(user.isEmpty())throw new UserException("Customer code not found");
            User updatedUser = user.get();
            String url = "https://api.paystack.co/customer/" + code;


            Customer customer = new Customer();
            customer.setFirst_name(updateCustomerRequest.getFirst_name());
           customer.setLast_name(updateCustomerRequest.getLast_name());
           customer.setPhone(updateCustomerRequest.getPhone());

          String body = convertObjToJson(customer);
          Response response = sendRequest(url,"PUT",body);
          PaystackResponse paystackResponse = handleApiResponse(response);


            if(response.isSuccessful()){
                saveUpdatedUser(updatedUser,customer);
            }

            response.close();
            return paystackResponse;

        } catch (UserException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PaystackResponse validateCustomer(String code, ValidateCustomerRequest validateCustomerRequest) {

        try{
            Optional<User> user = userRepository.findByCustomerCode(code);
            if(user.isEmpty())throw new UserException("Customer code not found");
            User validatedUser = user.get();
            String url = "https://api.paystack.co/customer/" + code+ "/identification";

            String body = convertObjToJson(validateCustomerRequest);
            Response response = sendRequest(url,"POST",body);
            PaystackResponse paystackResponse = handleApiResponse(response);

            if(response.isSuccessful()){
                saveValidatedUser(validatedUser,validateCustomerRequest);
            }

               response.close();
            return paystackResponse;

        } catch (UserException | IOException e) {
            throw new RuntimeException(e);
        }


    }

    private  void saveUser(User user,Customer customer,PaystackResponse paystackResponse){
        user.setEmail(customer.getEmail());
        String customerCode = (String) paystackResponse.getData().get("customer_code");
        user.setCustomerCode(customerCode);
        userRepository.save(user);
    }

    private void saveUpdatedUser(User user,Customer customer){
        user.setFirstName(customer.getFirst_name());
        user.setLastName(customer.getLast_name());
        user.setPhoneNumber(customer.getPhone());
        userRepository.save(user);
    }

    private void saveValidatedUser(User user, ValidateCustomerRequest validateCustomerRequest){
       user.setCountry(validateCustomerRequest.getCountry());
      user.setBvn(validateCustomerRequest.getBvn());
      user.setBank_code(validateCustomerRequest.getBank_code());
      user.setAccount_number(validateCustomerRequest.getAccount_number());
     userRepository.save(user);

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
