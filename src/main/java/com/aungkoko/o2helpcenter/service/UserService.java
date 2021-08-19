package com.aungkoko.o2helpcenter.service;

import com.aungkoko.o2helpcenter.controller.dto.UserRegistrationDto;
import com.aungkoko.o2helpcenter.model.AdminRequest;
import com.aungkoko.o2helpcenter.model.OxygenHelpCenter;
import com.aungkoko.o2helpcenter.model.Report;
import com.aungkoko.o2helpcenter.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    // for user and admin
    User saveAsUser(UserRegistrationDto userRegistrationDto);
    User saveAsAdmin(UserRegistrationDto userRegistrationDto);
    boolean isAlreadyRegistered(String email);
    User findByEmail(String email);

    // for all requests to be admin
    AdminRequest saveRequestToBeAdmin(AdminRequest adminRequest);
    Optional<AdminRequest> getRequestById(Long id);
    AdminRequest findRequestByEmail(String email);
    List<AdminRequest> getAllRequests();

    // for oxygen help center
    OxygenHelpCenter saveOxygenHelpCenter(OxygenHelpCenter oxygenHelpCenter);
    OxygenHelpCenter setVerifiedDataOfOxygenHelpCenter(Long id);
    OxygenHelpCenter setUnverifiedDataOfOxygenHelpCenter(Long id);
    OxygenHelpCenter setAvailableOxygen(Long id);
    OxygenHelpCenter setUnavailableOxygen(Long id);
    OxygenHelpCenter getOxygenCenterById(Long id);
    List<OxygenHelpCenter> getAllOxygenHelpCenters();
    void deleteOxygenHelpCenterById(Long id);
    void deleteAllOxygenHelpCenters();

    // for report
    ResponseEntity<? extends Object> saveReport(Report report);
    Report deleteReportById(Long id);
    void deleteAllReports();
    Report getReportById(Long id);
    List<Report> getAllReport();



}
