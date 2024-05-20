package br.com.challenge.newssenderapi.controller;

import br.com.challenge.newssenderapi.domain.News;
import br.com.challenge.newssenderapi.dto.request.NewsRequest;
import br.com.challenge.newssenderapi.dto.response.NewsResponse;
import br.com.challenge.newssenderapi.service.NewsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

    @GetMapping("/findAll")
    @ResponseStatus(HttpStatus.OK)
    public Page<News> listAll(@PageableDefault(sort ="title") Pageable pageable)throws Exception {
        return newsService.listAll(pageable);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteNews(@PathVariable Long id) throws Exception {
        newsService.deleteNews(id);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationErrorException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }
}
