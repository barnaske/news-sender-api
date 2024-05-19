package br.com.challenge.newssenderapi.service;

import br.com.challenge.newssenderapi.config.exception.ValidationException;
import br.com.challenge.newssenderapi.domain.Customer;
import br.com.challenge.newssenderapi.dto.request.CustomerRequest;
import br.com.challenge.newssenderapi.dto.response.CustomerResponse;
import br.com.challenge.newssenderapi.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public CustomerResponse save(CustomerRequest request) throws Exception{
        validateEmailAlreadyRegistered(request);

        var customer = customerRepository.save(Customer.of(request));

        return CustomerResponse.of(customer);
    }

    private void validateEmailAlreadyRegistered(CustomerRequest request) {
        var emailAlreadyRegistered = customerRepository.existsByEmail(request.getEmail());

        if (emailAlreadyRegistered)
            throw new ValidationException(String.format("O email '%s' já está cadastrado no newsletter", request.getEmail()));
    }
}
