package br.com.challenge.newssenderapi.service;

import br.com.challenge.newssenderapi.config.exception.ValidationException;
import br.com.challenge.newssenderapi.domain.News;
import br.com.challenge.newssenderapi.dto.request.NewsRequest;
import br.com.challenge.newssenderapi.dto.response.NewsResponse;
import br.com.challenge.newssenderapi.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    public NewsResponse save(NewsRequest request) throws Exception {
        var news = newsRepository.save(News.of(request));

        return NewsResponse.of(news);
    }

    public List<News> listAll(Pageable pageable) throws Exception {
        var news = newsRepository.findAll(pageable);

        if (news.isEmpty())
            throw new ValidationException("Não há notícias criadas.");

        return news;
    }

    public void deleteNews(Long id) throws Exception {
        News deleting = newsRepository.findById(id);

        if (deleting == null)
            throw new ValidationException(String.format("Nenhum notícia encontrada com o identificador informado: '%s'", id));

        var newsIntId = id.intValue();

        newsRepository.deleteById(newsIntId);
    }
}
