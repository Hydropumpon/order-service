package com.example.orderservice.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.beans.ConstructorProperties;

@Getter
@Builder
public class CustomerDto {

    private final Integer id;

    @Email
    private final String email;

    @NotNull
    @NotBlank
    private final String phoneNumber;

    @ConstructorProperties({"id", "email", "phoneNumber"})
    public CustomerDto(Integer id, @Email String email,
                       @NotNull @NotBlank String phoneNumber) {
        this.id = id;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
