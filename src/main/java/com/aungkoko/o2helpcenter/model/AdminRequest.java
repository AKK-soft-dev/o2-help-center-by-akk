package com.aungkoko.o2helpcenter.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "admin_request")
@NoArgsConstructor
@Getter
@Setter
@ApiModel(description = "Details for requesting admin role!")
public class AdminRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "username must not be null")
    @ApiModelProperty(value = "Username must not be null!")
    private String username;

    @NotNull(message = "email must not be null")
    @ApiModelProperty(value = "Email must not be null!")
    private String email;

    @NotNull(message = "password must not be null")
    @Min(value = 8, message = "Password length must be longer than 7")
    @ApiModelProperty(value = "Password must not be null and its length must be longer than 7!")
    private String password;

    public AdminRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
