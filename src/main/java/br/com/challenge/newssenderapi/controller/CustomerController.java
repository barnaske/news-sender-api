package br.com.challenge.newssenderapi.controller;

import br.com.challenge.newssenderapi.domain.Customer;
import br.com.challenge.newssenderapi.dto.request.CustomerRequest;
import br.com.challenge.newssenderapi.dto.response.CustomerResponse;
import br.com.challenge.newssenderapi.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
}
