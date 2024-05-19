package br.com.challenge.newssenderapi.dto.response;

import br.com.challenge.newssenderapi.domain.Customer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.util.BeanUtil;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;

@Data
public class CustomerResponse {

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;

    public static CustomerResponse of(Customer customer){
        var response = new CustomerResponse();

        BeanUtils.copyProperties(customer, response);

        return response;
    }
}
