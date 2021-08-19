package com.aungkoko.o2helpcenter.controller.admin;

import com.aungkoko.o2helpcenter.controller.dto.UserRegistrationDto;
import com.aungkoko.o2helpcenter.model.AdminRequest;
import com.aungkoko.o2helpcenter.model.OxygenHelpCenter;
import com.aungkoko.o2helpcenter.model.Report;
import com.aungkoko.o2helpcenter.model.User;
import com.aungkoko.o2helpcenter.response.ResponseMessage;
import com.aungkoko.o2helpcenter.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
@ApiModel(description = "This controller control admin action and all endponts in this controller can only be accessed by admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/hello")
    public String hello(){
        return "Hello Admin!";
    }

    @PostMapping("/allowToBeAdmin/{id}")
    @ApiModelProperty(value = "This endpoint is used to allow user who request to be admin")
    public ResponseEntity<Object> allowToBeAdmin(@PathVariable Long id){
        Optional<AdminRequest> adminRequest = userService.getRequestById(id);
        if(!adminRequest.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage<Object>(false,"Request not found!",null));
        }
        User user = userService.saveAsAdmin(new UserRegistrationDto(
                adminRequest.get().getUsername(),
                adminRequest.get().getEmail(),
                adminRequest.get().getPassword()
                ));
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage<User>(true,"Allowed!",user));
    }

    @GetMapping("/findUserByEmail")
    @ApiModelProperty(value = "This endpoint is used to find specific user")
    public ResponseEntity<Object> findUserByEmail(@RequestParam(value = "email",required = true) String email){
        User user = userService.findByEmail(email);
        if(user == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage<User>(false,"User not found!",null));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseMessage<User>(true,"Found!",user));
    }

    @GetMapping("/getRequestById/{id}")
    @ApiModelProperty(value = "This endpoint is used to find request that can be admin")
    public ResponseEntity<Object> getRequestById(@PathVariable("id")Long id){
        Optional<AdminRequest> adminRequest = userService.getRequestById(id);
        if(!adminRequest.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage<Object>(false,"Not found!",null));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseMessage<AdminRequest>(true,"Found!",adminRequest.get()));
    }

    @GetMapping("/getAllRequests")
    @ApiModelProperty(value = "This endpoint is used to find the list of requests")
    public ResponseEntity<Object> getAllRequests(){
        List<AdminRequest> adminRequestList = userService.getAllRequests();
        if(adminRequestList.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage<Object>(false,"No requests here!",null));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseMessage<List<AdminRequest>>(true,"Here is request lists",adminRequestList));
    }

    // when admins uploaded oxygen help center, the data are automatically verified
    // so if the admins wanna upload data, you all must confirm data
    @PostMapping("/saveOxygenHelpCenter")
    @ApiModelProperty(value = "This endpoint is used to save data of oxygen help center")
    public ResponseEntity<Object> saveOxygenHelpCenter(@RequestBody @Validated OxygenHelpCenter oxygenHelpCenter){
        oxygenHelpCenter.setVerified(true);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseMessage<OxygenHelpCenter>(true,"Saved successfully!",userService.saveOxygenHelpCenter(oxygenHelpCenter)));
    }

    @PostMapping("/setVerifiedDataOfOxygenHelpCenter/{id}")
    @ApiModelProperty(value = "This endpoint is used to set verified data of help center so that user can know it is sure or not")
    public ResponseEntity<Object> setVerifiedData(@PathVariable("id")Long id){
        OxygenHelpCenter oxygenHelpCenter = userService.setVerifiedDataOfOxygenHelpCenter(id);
        if(oxygenHelpCenter == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage<Object>(false,"Not found data!",null));
        }else{
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseMessage<OxygenHelpCenter>(true,"set verified successfully!",oxygenHelpCenter));
        }
    }

    @PostMapping("/setUnverifiedDataOfOxygenHelpCenter/{id}")
    @ApiModelProperty(value = "This endpoint is used to set unverified data of help center so that user can know it is sure or not")
    public ResponseEntity<Object> setUnverifiedData(@PathVariable("id")Long id){
        OxygenHelpCenter oxygenHelpCenter = userService.setUnverifiedDataOfOxygenHelpCenter(id);
        if(oxygenHelpCenter == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage<Object>(false,"Not found data!",null));
        }else{
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseMessage<OxygenHelpCenter>(true,"set unverified successfully!",oxygenHelpCenter));
        }
    }

    @PostMapping("/setAvailableOxygen/{id}")
    @ApiModelProperty(value = "This endpoint is used to set available so that user can know oxygen is still available or not")
    public ResponseEntity<Object> setAvailableOxygen(@PathVariable("id")Long id){
        OxygenHelpCenter oxygenHelpCenter = userService.setAvailableOxygen(id);
        if(oxygenHelpCenter == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage<Object>(false,"Not found data!",null));
        }else{
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseMessage<OxygenHelpCenter>(true,"set available successfully!",oxygenHelpCenter));
        }
    }

    @PostMapping("/setUnavailableOxygen/{id}")
    @ApiModelProperty(value = "This endpoint is used to set unavailable so that user can know oxygen is still available or not")
    public ResponseEntity<Object> setUnavailableOxygen(@PathVariable("id")Long id){
        OxygenHelpCenter oxygenHelpCenter = userService.setUnavailableOxygen(id);
        if(oxygenHelpCenter == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage<Object>(false,"Not found data!",null));
        }else{
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseMessage<OxygenHelpCenter>(true,"set unavailable successfully!",oxygenHelpCenter));
        }
    }

    @DeleteMapping("/deleteReportById/{report_id}")
    @ApiModelProperty(value = "This endpoint is used to delete specific report")
    public ResponseEntity<Object> deleteReportById(@PathVariable("report_id") Long report_id){
        Report report = userService.deleteReportById(report_id);
        if(report == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage<Object>(false,"Report doesn't exist with this id "+report_id+" !",null));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseMessage<Object>(false,"Deleted report with this id "+report_id+" !",report));
    }

    @DeleteMapping("/deleteAllReports")
    @ApiModelProperty(value = "This endpoint is used to delete all reports")
    public ResponseEntity<Object> deleteAllReports(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseMessage<Object>(true,"Deleted all reports!",null));
    }

    @GetMapping("/findReportById/{report_id}")
    @ApiModelProperty(value = "This endpoint is used to find specific report")
    public ResponseEntity<Object> findReportById(@PathVariable("report_id")Long id){
        Report report = userService.getReportById(id);
        if(report != null){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseMessage<Report>(true,"Found",report));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseMessage<Report>(false,"Not found!",null));

    }

    @GetMapping("/findAllReports")
    @ApiModelProperty(value = "This endpoint is used to find all reports")
    public ResponseEntity<Object> findAllReports(){
        List<Report> reports = userService.getAllReport();
        if(reports.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage<Object>(false,"Reports not found!",reports));
        }else return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseMessage<Object>(false,"Found "+reports.size()+" reports!",reports));
    }

    @DeleteMapping("/deleteOxygenHelpCenterById/{id}")
    @ApiModelProperty(value = "This endpoint is used to delete specific data of oxygen help center")
    public ResponseEntity<Object> deleteOxygenHelpCenterById(@PathVariable("id")Long id){
        OxygenHelpCenter oxygenHelpCenter = userService.getOxygenCenterById(id);
        if(oxygenHelpCenter == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage<Object>(false,"Help center with this id "+id+" not found to delete!",null));
        }
        userService.deleteOxygenHelpCenterById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseMessage<Object>(true,"Deleted!",null));
    }

    @DeleteMapping("/deleteAllOxygenHelpCenters")
    @ApiModelProperty(value = "This endpoint is used to delete all data of oxygen help centers.Please consider carefully before you use this endpoint")
    public ResponseEntity<Object> deleteAllOxygenHelpCenters(@PathVariable("id")Long id){
        userService.deleteAllOxygenHelpCenters();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseMessage<Object>(true,"Deleted all oxygen help centers!",null));
    }

}
