package br.com.challenge.newssenderapi.controller;

import br.com.challenge.newssenderapi.dto.request.NewsRequest;
import br.com.challenge.newssenderapi.dto.response.NewsResponse;
import br.com.challenge.newssenderapi.service.NewsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/news")
@Valid
public class NewsController {

    @Autowired
    private NewsService newsService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewsResponse save(@RequestBody @Valid NewsRequest request) throws Exception{
        return newsService.save(request);
    }
}
