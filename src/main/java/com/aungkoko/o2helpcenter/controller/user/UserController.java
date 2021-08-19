package com.aungkoko.o2helpcenter.controller.user;

import com.aungkoko.o2helpcenter.model.OxygenHelpCenter;
import com.aungkoko.o2helpcenter.model.Report;
import com.aungkoko.o2helpcenter.response.ResponseMessage;
import com.aungkoko.o2helpcenter.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@ApiModel(description = "This controller control user actions and all endpoints in this controller can be accessed by both admin and user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/hello")
    public String hello(){
        return "Hello User!";
    }

    // if the users upload the data, they will not be able to verify that data.
    @PostMapping("/saveOxygenHelpCenter")
    @ApiModelProperty(value = "This endpoint is used to save data of oxygen help center")
    public ResponseEntity<Object> saveOxygenHelpCenter(@RequestBody @Validated OxygenHelpCenter oxygenHelpCenter){
        oxygenHelpCenter.setVerified(false);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseMessage<OxygenHelpCenter>(true,"Saved successfully!",userService.saveOxygenHelpCenter(oxygenHelpCenter)));
    }

    @PostMapping("/report")
    @ApiModelProperty(value = "This endpoint is used to report data of oxygen help center")
    public ResponseEntity<?> report(@RequestBody @Validated Report report){
        return userService.saveReport(report);
    }

    @GetMapping("/findOxygenHelpCenterById/{id}")
    @ApiModelProperty(value = "This endpoint is used to find specific data of oxygen help center")
    public ResponseEntity<Object> findOxygenHelpCenterById(@PathVariable("id")Long id){
        OxygenHelpCenter oxygenHelpCenter = userService.getOxygenCenterById(id);
        if(oxygenHelpCenter == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage<Object>(false,"Help center not found with this id "+id+"!",null));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseMessage<OxygenHelpCenter>(true,"Help center found!",oxygenHelpCenter));
    }

    @GetMapping("/findAllOxygenHelpCenters")
    @ApiModelProperty(value = "This endpoint is used to find all data of oxygen help center")
    public ResponseEntity<Object> findAllOxygenHelpCenters(){
        List<OxygenHelpCenter> oxygenHelpCenters = userService.getAllOxygenHelpCenters();
        if(oxygenHelpCenters.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage<Object>(false,"Help centers not exist yet!",null));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseMessage<List<OxygenHelpCenter>>(true,"Found "+oxygenHelpCenters.size()+" help centers!",oxygenHelpCenters));
    }
}
