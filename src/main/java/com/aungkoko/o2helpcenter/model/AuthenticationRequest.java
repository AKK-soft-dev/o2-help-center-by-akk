package com.aungkoko.o2helpcenter.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class AuthenticationRequest {

    @NotNull(message = "email must not be null")
    @ApiModelProperty(value = "Email must not be null")
    private String email;

    @NotNull(message = "password must not be null")
    @Min(value = 8, message = "Password length must be longer than 7")
    @ApiModelProperty(value = "Password must not be null and its length must be longer than 7!")
    private String password;

    public AuthenticationRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
