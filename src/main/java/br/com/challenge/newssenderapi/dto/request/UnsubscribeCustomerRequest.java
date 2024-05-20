package br.com.challenge.newssenderapi.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UnsubscribeCustomerRequest {

    @NotBlank(message = "O campo email é obrigatório para cancelar a inscrição")
    @Email(message = "Email em formato inválido.")
    private String email;
}
