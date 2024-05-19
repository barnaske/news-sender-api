package br.com.challenge.newssenderapi.domain;

import br.com.challenge.newssenderapi.dto.request.CustomerRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

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

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;

    public static Customer of(CustomerRequest request){
        var customer = new Customer();

        BeanUtils.copyProperties(request, customer);

        return customer;
    }
}
