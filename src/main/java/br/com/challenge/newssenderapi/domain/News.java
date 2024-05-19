package br.com.challenge.newssenderapi.domain;

import br.com.challenge.newssenderapi.dto.request.NewsRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "News")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @NotBlank
    private String description;

    private String link;

    public static News of(NewsRequest request){
        var news = new News();

        BeanUtils.copyProperties(request, news);

        return news;
    }
}
