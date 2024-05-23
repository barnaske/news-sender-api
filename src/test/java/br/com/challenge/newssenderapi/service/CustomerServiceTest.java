package br.com.challenge.newssenderapi.service;


import br.com.challenge.newssenderapi.config.exception.ValidationException;
import br.com.challenge.newssenderapi.domain.Customer;
import br.com.challenge.newssenderapi.dto.request.CustomerRequest;
import br.com.challenge.newssenderapi.dto.response.CustomerResponse;
import br.com.challenge.newssenderapi.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository repository;

    @InjectMocks
    private CustomerService service;

    @Test
    @DisplayName("Não deve salvar quando email já cadastrado")
    void dontSaveWhenEmailAlreadyRegistered() throws Exception {
        var customer = new Customer(1L,
                "Augusto Barnasake",
                "augustobarnaske@hotmail.com",
                LocalDate.of(1996,10,2)
        );

        var request = CustomerRequest.builder()
                .name(customer.getName())
                .email(customer.getEmail())
                .birthdate(customer.getBirthdate())
                .build();

        when(repository.existsByEmail(customer.getEmail())).thenReturn(true);

        var exception = assertThrows(ValidationException.class, () -> service.save(request));

        assertTrue(exception.getMessage().contains(request.getEmail()));

        verify(repository, never()).save(Customer.of(request));
    }

    @Test
    @DisplayName("Deve salvar com sucesso")
    void saveSuccessfully() throws Exception{
        var customer = new Customer(1L,
                "Augusto Barnasake",
                "augustobarnaske@hotmail.com",
                LocalDate.of(1996,10,2)
        );

        var request = CustomerRequest.builder()
                .name(customer.getName())
                .email(customer.getEmail())
                .birthdate(customer.getBirthdate())
                .build();

        when(repository.existsByEmail(customer.getEmail())).thenReturn(false);
        when(repository.save(Customer.of(request))).thenReturn(customer);

        service.save(request);

        verify(repository, times(1)).save(Customer.of(request));
    }

    @Test
    @DisplayName("Não deve listar nenhum cliente pois a lista está vazia")
    void doNotListSubscribedBecauseListIsEmpty() throws Exception{
        Pageable pageable = PageRequest.of(0,10);

        List<Customer> subscribed = emptyList();

        when(repository.findAll(pageable)).thenReturn(subscribed);

        var exception = assertThrows(ValidationException.class, () -> service.listSubscribed(pageable));

        assertTrue(exception.getMessage().equals("Não há clientes inscritos na newsletter."));
    }

    @Test
    @DisplayName("Deve listar inscritos")
    void listSubscribed() throws Exception{
        var customer = new Customer(1L,
                "Augusto Barnaske",
                "augustobarnaske@hotmail.com",
                LocalDate.of(1996,10,2)
        );

        Pageable pageable = PageRequest.of(0,10);

        List<Customer> subscribed = new ArrayList<>();
        subscribed.add(customer);

        when(repository.findAll(pageable)).thenReturn(subscribed);

        service.listSubscribed(pageable);

        verify(repository, times(1)).findAll(pageable);

    }

    @Test
    @DisplayName("Não foi possível cancelar inscrição pois não existe email cadastrado")
    void unableToUnsubBecauseThereIsNoEmailRegistered() throws Exception{
        var email = "noMatch@email.com";

        Customer customer = null;

        when(repository.findByEmail(email)).thenReturn(null);

        var exception = assertThrows(ValidationException.class, () -> service.unsubscribe(email));

        assertTrue(exception.getMessage().contains(email));

        verify(repository, never()).delete(customer);
    }

    @Test
    @DisplayName("Cancelar inscrição")
    void unsubscribe() throws Exception{
        var customer = new Customer(1L,
                "Augusto Barnasake",
                "augustobarnaske@hotmail.com",
                LocalDate.of(1996,10,2)
        );

        when(repository.findByEmail(customer.getEmail())).thenReturn(customer);

        service.unsubscribe(customer.getEmail());

        verify(repository, times(1)).delete(customer);
    }
}