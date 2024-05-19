package br.com.challenge.newssenderapi.dto.response;

import br.com.challenge.newssenderapi.domain.News;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.beans.BeanUtils;


@Data
public class NewsResponse {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    private String link;

    public static NewsResponse of(News news){
        var response = new NewsResponse();

        BeanUtils.copyProperties(news, response);

        return response;
    }
}
