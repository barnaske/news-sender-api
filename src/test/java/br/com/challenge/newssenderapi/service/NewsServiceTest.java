package br.com.challenge.newssenderapi.service;

import br.com.challenge.newssenderapi.config.exception.ValidationException;
import br.com.challenge.newssenderapi.domain.Customer;
import br.com.challenge.newssenderapi.domain.News;
import br.com.challenge.newssenderapi.dto.request.NewsRequest;
import br.com.challenge.newssenderapi.repository.NewsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NewsServiceTest {

    @Mock
    private NewsRepository repository;

    @InjectMocks
    private NewsService service;

    @Test
    @DisplayName("Salvar notícia")
    void save() throws Exception{
        var news = new News(1L,
                "Title",
                "Desc",
                null,
                false
        );

        var request = NewsRequest.builder()
                .title(news.getTitle())
                .description(news.getDescription())
                .link(news.getLink())
                .sent(false)
                .build();

        when(repository.save(News.of(request))).thenReturn(news);

        service.save(request);

        verify(repository, times(1)).save(News.of(request));
    }

    @Test
    @DisplayName("Não listar noticias pois lista está vazia")
    void dontListAll() {
        Pageable pageable = PageRequest.of(0,10);

        List<News> news = emptyList();

        when(repository.findAll(pageable)).thenReturn(news);

        var exception = assertThrows(ValidationException.class, () -> service.listAll(pageable));

        assertTrue(exception.getMessage().equals("Não há notícias criadas."));
    }

    @Test
    @DisplayName("Listar notícias")
    void listAll() throws Exception {
        var news = new News(1L,
                "Title",
                "Desc",
                null,
                false
        );

        Pageable pageable = PageRequest.of(0,10);

        List<News> newsToList = new ArrayList<>();
        newsToList.add(news);

        when(repository.findAll(pageable)).thenReturn(newsToList);

        service.listAll(pageable);

        verify(repository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Não deletar pois não foi encontrado registro pelo identificador passado")
    void dontDeleteNews() {
        var newsId = 10L;
        var newsIdInt = 10;

        when(repository.findById(newsId)).thenReturn(null);

        var exception = assertThrows(ValidationException.class, () -> service.deleteNews(newsId));

        assertTrue(exception.getMessage().contains("Nenhum notícia encontrada com o identificador informado"));

        verify(repository, never()).deleteById(newsIdInt);
    }

    @Test
    @DisplayName("Deletar noticia")
    void deleteNews() throws Exception {
        var news = new News(1L,
                "Title",
                "Desc",
                null,
                false
        );

        when(repository.findById(news.getId())).thenReturn(news);

        service.deleteNews(news.getId());

        verify(repository, times(1)).deleteById(news.getId().intValue());
    }
}