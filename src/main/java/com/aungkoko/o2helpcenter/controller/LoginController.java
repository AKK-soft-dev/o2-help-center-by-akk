package com.aungkoko.o2helpcenter.controller;

import com.aungkoko.o2helpcenter.model.AuthenticationRequest;
import com.aungkoko.o2helpcenter.response.AuthenticationResponse;
import com.aungkoko.o2helpcenter.response.ResponseMessage;
import com.aungkoko.o2helpcenter.service.UserService;
import com.aungkoko.o2helpcenter.util.JwtUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@ApiModel(value = "This controller control authentication")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtilToken;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @ApiModelProperty(value = "This endpoint is used to get token so that users can access resources")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody @Validated AuthenticationRequest authenticationRequest) throws Exception{

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),authenticationRequest.getPassword()));
        }catch (BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new AuthenticationResponse(false,null));
        }

        final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getEmail());

        final String token = jwtUtilToken.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(true,token));
    }

}
