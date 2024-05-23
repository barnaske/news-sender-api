package br.com.challenge.newssenderapi.service;

import br.com.challenge.newssenderapi.config.exception.ValidationException;
import br.com.challenge.newssenderapi.domain.Customer;
import br.com.challenge.newssenderapi.dto.request.CustomerRequest;
import br.com.challenge.newssenderapi.dto.response.CustomerResponse;
import br.com.challenge.newssenderapi.dto.response.UnsubscribeCustomerResponse;
import br.com.challenge.newssenderapi.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Optional.of;
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

    public List<Customer> listSubscribed(Pageable pageable) throws Exception {

        var subscribed = customerRepository.findAll(pageable);

        if (subscribed.isEmpty())
            throw new ValidationException("Não há clientes inscritos na newsletter.");

        return subscribed;
    }

    public UnsubscribeCustomerResponse unsubscribe(String email) throws Exception{
        Customer unsub = customerRepository.findByEmail(email);

        if (unsub == null)
            throw new ValidationException(String.format("Nenhum cliente cadastrado com o email informado '%s'", email));

        customerRepository.delete(unsub);

        return UnsubscribeCustomerResponse.of(unsub);
    }

    private void validateEmailAlreadyRegistered(CustomerRequest request) {
        var emailAlreadyRegistered = customerRepository.existsByEmail(request.getEmail());

        if (emailAlreadyRegistered)
            throw new ValidationException(String.format("O email '%s' já está cadastrado no newsletter", request.getEmail()));
    }

}
