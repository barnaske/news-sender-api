package br.com.challenge.newssenderapi.repository;

import br.com.challenge.newssenderapi.domain.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends CrudRepository<News, Integer> {

    News findById(Long id);

    Page<News> findAll(Pageable pageable);

    List<News> findBySentFalse();

}
