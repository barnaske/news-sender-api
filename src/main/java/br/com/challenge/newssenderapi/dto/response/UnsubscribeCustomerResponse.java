package br.com.challenge.newssenderapi.dto.response;

import br.com.challenge.newssenderapi.domain.Customer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class UnsubscribeCustomerResponse {

    @NotBlank(message = "O campo email é obrigatório para cancelar a inscrição")
    @Email(message = "Email em formato inválido.")
    private String email;

    public static UnsubscribeCustomerResponse of(Customer customer){
        var response = new UnsubscribeCustomerResponse();

        BeanUtils.copyProperties(customer, response);

        return response;
    }
}
