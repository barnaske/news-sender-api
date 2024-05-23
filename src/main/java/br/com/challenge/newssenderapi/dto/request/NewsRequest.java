package br.com.challenge.newssenderapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewsRequest {

    @NotBlank(message = "O campo título é obrigatório para cadastro de uma notícia")
    private String title;

    @NotBlank(message = "O campo descrição é obrigatório para cadastro de uma notícia")
    private String description;

    private String link;

    @NotNull
    private boolean sent = false;
}
