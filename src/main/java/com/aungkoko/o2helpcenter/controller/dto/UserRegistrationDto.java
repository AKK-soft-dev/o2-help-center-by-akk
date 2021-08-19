package com.aungkoko.o2helpcenter.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class UserRegistrationDto {

    @NotNull(message = "username must not be null")
    private String username;
    @NotNull(message = "email must not be null")
    private String email;
    @NotNull(message = "password must not be null")
    private String password;

    public UserRegistrationDto(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
