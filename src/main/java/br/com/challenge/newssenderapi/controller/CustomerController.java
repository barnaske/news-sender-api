package br.com.challenge.newssenderapi.controller;

import br.com.challenge.newssenderapi.domain.Customer;
import br.com.challenge.newssenderapi.dto.request.CustomerRequest;
import br.com.challenge.newssenderapi.dto.request.UnsubscribeCustomerRequest;
import br.com.challenge.newssenderapi.dto.response.CustomerResponse;
import br.com.challenge.newssenderapi.dto.response.UnsubscribeCustomerResponse;
import br.com.challenge.newssenderapi.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customers")
@Valid
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse save(@RequestBody @Valid CustomerRequest request) throws Exception {
        return customerService.save(request);
    }

    @GetMapping("/findAll")
    @ResponseStatus(HttpStatus.OK)
    public List<Customer> listSubscribed(@PageableDefault(sort = "name") Pageable pageable) throws Exception {
        return customerService.listSubscribed(pageable);
    }

    @DeleteMapping("/unsubscribe")
    @ResponseStatus(HttpStatus.OK)
    public UnsubscribeCustomerResponse unsubscribe(@RequestBody UnsubscribeCustomerRequest request) throws Exception {
        return customerService.unsubscribe(request.getEmail());
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationErrorException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }
}
