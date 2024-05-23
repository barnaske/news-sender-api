package br.com.challenge.newssenderapi.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class CustomerRequest {

    @NotBlank(message = "O campo nome é obrigatório para cadastro de um cliente")
    private String name;

    @NotBlank(message = "O campo email é obrigatório para cadastro de um cliente")
    @Email(message = "Email em formato incorreto.")
    private String email;

    private LocalDate birthdate;
}
