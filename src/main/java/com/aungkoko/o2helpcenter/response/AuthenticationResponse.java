package com.aungkoko.o2helpcenter.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationResponse {

    private boolean con;
    private final String token;

    public AuthenticationResponse(boolean con,String token) {
        this.con = con;
        this.token = token;
    }
}
