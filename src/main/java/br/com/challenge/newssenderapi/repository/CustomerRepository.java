package br.com.challenge.newssenderapi.repository;

import br.com.challenge.newssenderapi.domain.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {
}
