package br.com.challenge.newssenderapi.repository;

import br.com.challenge.newssenderapi.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    boolean existsByEmail(String email);

    Customer findById(Long id);

    Page<Customer> findAll(Pageable pageable);

}
