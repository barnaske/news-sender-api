package br.com.challenge.newssenderapi.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório para o cadastro de um cliente")
    private String name;

    @NotBlank(message = "Email é obrigatório para o cadastro de um cliente")
    private String email;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;
}
