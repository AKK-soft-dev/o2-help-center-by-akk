package com.aungkoko.o2helpcenter.controller;

import com.aungkoko.o2helpcenter.controller.dto.UserRegistrationDto;
import com.aungkoko.o2helpcenter.model.User;
import com.aungkoko.o2helpcenter.response.ResponseMessage;
import com.aungkoko.o2helpcenter.service.UserService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@ApiModel(description = "This controller control registration")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @PostMapping("/registerForUser")
    @ApiModelProperty(value = "This endpoint is used to register user")
    public ResponseEntity<Object> registerForUser(@RequestBody @Validated UserRegistrationDto userRegistrationDto){
        if(userService.isAlreadyRegistered(userRegistrationDto.getEmail())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseMessage<Object>(false,"Already registered with this email "+userRegistrationDto.getEmail() + " !",null));
        }
        User user = userService.saveAsUser(userRegistrationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage<User>(true,"Registered! To consume our services, please login!",user));
    }

}
