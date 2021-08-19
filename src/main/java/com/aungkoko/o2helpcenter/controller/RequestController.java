package com.aungkoko.o2helpcenter.controller;

import com.aungkoko.o2helpcenter.model.AdminRequest;
import com.aungkoko.o2helpcenter.model.Role;
import com.aungkoko.o2helpcenter.model.User;
import com.aungkoko.o2helpcenter.response.ResponseMessage;
import com.aungkoko.o2helpcenter.service.UserService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/*
  This class is for requesting to be admin, so that they can add data
*/
@RestController
@ApiModel(description = "The endpoints in this controller do not need token")
public class RequestController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String firstState(){
        //userService.storeMeAsAdmin();
        return "Hello";
    }

    @PostMapping("/requestToBeAdmin")
    @ApiModelProperty(value = "This endpoint is used to request to be admin who can manage data")
    public ResponseEntity<Object> requestToBeAdmin(@RequestBody @Validated AdminRequest adminRequest){

        AdminRequest adminRequest1 = userService.findRequestByEmail(adminRequest.getEmail());

        if(adminRequest1 != null){

            User user = userService.findByEmail(adminRequest1.getEmail());
            if(user != null){
                List<Role> roles = user.getRoles().stream().collect(Collectors.toList());
                for(Role role : roles){
                    if(role.getName().equals("ROLE_ADMIN")){
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(new ResponseMessage<Object>(
                                        false,
                                        "You are already an admin!",
                                        null
                                ));
                    }
                }
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseMessage<Object>(false,"You already requested with this email +" +adminRequest.getEmail() + " !",null));

        }else{
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseMessage<AdminRequest>(
                            true,
                            "Requested! Please wait for admin response! And also please save your id!",
                            userService.saveRequestToBeAdmin(adminRequest)
                    )
            );
        }
    }

    @GetMapping("/checkIfAllowed/{requestId}/{email}")
    @ApiModelProperty(value = "This endpoint is used to check whether user is allowed to be admin or not")
    public ResponseEntity<Object> checkIfAllowed(@PathVariable("requestId")Long id, @PathVariable("email") String email){

        Optional<AdminRequest> adminRequest = userService.getRequestById(id);
        AdminRequest adminRequest1 = userService.findRequestByEmail(email);

        if(!adminRequest.isPresent() || adminRequest1 == null){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseMessage<Object>(false,"We didn't find any request! Maybe you got reject!",null));
        }else{
            if(adminRequest1.getEmail().equals(adminRequest.get().getEmail()) && adminRequest.get().getId() == adminRequest1.getId()){
                if(userService.isAlreadyRegistered(email)){
                    return ResponseEntity.status(HttpStatus.OK)
                            .body(new ResponseMessage<AdminRequest>(
                                    true,
                                    "Congrats! You have been allowed! Please login with your email and password!",
                                    adminRequest.get()
                            ));
                }
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                        .body(new ResponseMessage<Object>(false,"Wait for it!",null));
            }else{
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                        .body(new ResponseMessage<Object>(false,"Don't cheat on our service!",null));
            }
        }

    }

}
