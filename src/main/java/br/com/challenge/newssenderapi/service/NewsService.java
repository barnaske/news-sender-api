package br.com.challenge.newssenderapi.service;

import br.com.challenge.newssenderapi.config.exception.ValidationException;
import br.com.challenge.newssenderapi.domain.News;
import br.com.challenge.newssenderapi.dto.request.NewsRequest;
import br.com.challenge.newssenderapi.dto.response.NewsResponse;
import br.com.challenge.newssenderapi.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    public NewsResponse save(NewsRequest request) throws Exception {
        var news = newsRepository.save(News.of(request));

        return NewsResponse.of(news);
    }
}
