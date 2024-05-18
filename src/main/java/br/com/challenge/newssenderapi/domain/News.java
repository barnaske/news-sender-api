package br.com.challenge.newssenderapi.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @NotBlank(message = "Titúlo é obrigatório para o cadastro de uma notícia")
    private String title;

    @NotBlank(message = "Descrição é obrigatória para o cadastro de uma notícia")
    private String description;

    private String link;

}
