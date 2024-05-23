package br.com.challenge.newssenderapi.repository;

import br.com.challenge.newssenderapi.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    boolean existsByEmail(String email);

    Customer findById(Long id);

    List<Customer> findAll(Pageable pageable);

    List<Customer> findAll();

    Customer findByEmail(String email);

}
