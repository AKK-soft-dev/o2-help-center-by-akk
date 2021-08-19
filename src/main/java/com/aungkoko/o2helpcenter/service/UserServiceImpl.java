package com.aungkoko.o2helpcenter.service;

import com.aungkoko.o2helpcenter.controller.dto.UserRegistrationDto;
import com.aungkoko.o2helpcenter.model.*;
import com.aungkoko.o2helpcenter.repository.AdminRequestRepository;
import com.aungkoko.o2helpcenter.repository.O2HelpCenterRepository;
import com.aungkoko.o2helpcenter.repository.ReportRepository;
import com.aungkoko.o2helpcenter.repository.UserRepository;
import com.aungkoko.o2helpcenter.response.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRequestRepository adminRequestRepository;

    @Autowired
    private O2HelpCenterRepository o2HelpCenterRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /* -- For authentication -- */

    @Override
    public User saveAsUser(UserRegistrationDto userRegistrationDto) {
        User user = new User(
                userRegistrationDto.getUsername(),
                userRegistrationDto.getEmail(),
                passwordEncoder.encode(userRegistrationDto.getPassword()),
                Arrays.asList(new Role("ROLE_USER"))
        );
        return userRepository.save(user);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void storeMeAsAdmin(){ // this method will automatically run after spring boot starts

        User user = userRepository.findByEmail("aungkokosoftdev@gmail.com");
        if(user == null){
            user = new User(
                    "Aung Ko Ko",
                    "aungkokosoftdev@gmail.com",
                    passwordEncoder.encode("106604Moe"),
                    Arrays.asList(new Role("ROLE_ADMIN"))
            );
            userRepository.save(user);
            System.out.println("Saved!");
        }
        System.out.println("Running...");

    }

    @Override
    public User saveAsAdmin(UserRegistrationDto userRegistrationDto) {

        User user = userRepository.findByEmail(userRegistrationDto.getEmail());

        if(user != null && user.getRoles().stream().collect(Collectors.toList()).get(0).getName().equals("ROLE_USER")){
            user.getRoles().add(new Role("ROLE_ADMIN"));
            return userRepository.save(user);
        } else{
            User user1 = new User(
                    userRegistrationDto.getUsername(),
                    userRegistrationDto.getEmail(),
                    passwordEncoder.encode(userRegistrationDto.getPassword()),
                    Arrays.asList(new Role("ROLE_ADMIN"))
            );
            return userRepository.save(user);
        }

    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /* -- For all requests to be admin -- */

    @Override
    public AdminRequest saveRequestToBeAdmin(AdminRequest adminRequest) {
        return adminRequestRepository.save(adminRequest);
    }

    @Override
    public Optional<AdminRequest> getRequestById(Long id) {
        return adminRequestRepository.findById(id);
    }

    @Override
    public AdminRequest findRequestByEmail(String email) {
        return adminRequestRepository.findByEmail(email);
    }

    @Override
    public List<AdminRequest> getAllRequests() {
        return adminRequestRepository.findAll();
    }

    @Override
    public boolean isAlreadyRegistered(String email) {
        User user = userRepository.findByEmail(email);
        if(user != null){
            return true;
        }
        return false;
    }

    /* --  For oxygen help center -- */

    @Override
    public OxygenHelpCenter saveOxygenHelpCenter(OxygenHelpCenter oxygenHelpCenter) {
        return o2HelpCenterRepository.save(oxygenHelpCenter);
    }

    @Override
    public OxygenHelpCenter setVerifiedDataOfOxygenHelpCenter(Long id) {
        Optional<OxygenHelpCenter> oxygenHelpCenter = o2HelpCenterRepository.findById(id);
        if(!oxygenHelpCenter.isPresent()){
            return null;
        }else{
            oxygenHelpCenter.get().setVerified(true);
            return o2HelpCenterRepository.save(oxygenHelpCenter.get());
        }
    }

    @Override
    public OxygenHelpCenter setUnverifiedDataOfOxygenHelpCenter(Long id) {
        Optional<OxygenHelpCenter> oxygenHelpCenter = o2HelpCenterRepository.findById(id);
        if(!oxygenHelpCenter.isPresent()){
            return null;
        }else{
            oxygenHelpCenter.get().setVerified(false);
            return o2HelpCenterRepository.save(oxygenHelpCenter.get());
        }
    }

    @Override
    public OxygenHelpCenter setAvailableOxygen(Long id) {
        Optional<OxygenHelpCenter> oxygenHelpCenter = o2HelpCenterRepository.findById(id);
        if(!oxygenHelpCenter.isPresent()){
            return null;
        }else{
            oxygenHelpCenter.get().setStillAvailable(true);
            return o2HelpCenterRepository.save(oxygenHelpCenter.get());
        }
    }

    @Override
    public OxygenHelpCenter setUnavailableOxygen(Long id) {
        Optional<OxygenHelpCenter> oxygenHelpCenter = o2HelpCenterRepository.findById(id);
        if(!oxygenHelpCenter.isPresent()){
            return null;
        }else{
            oxygenHelpCenter.get().setStillAvailable(false);
            return o2HelpCenterRepository.save(oxygenHelpCenter.get());
        }
    }

    @Override
    public OxygenHelpCenter getOxygenCenterById(Long id) {
        return o2HelpCenterRepository.findById(id).orElse(null);
    }

    @Override
    public List<OxygenHelpCenter> getAllOxygenHelpCenters() {
        return o2HelpCenterRepository.findAll();
    }

    @Override
    public void deleteOxygenHelpCenterById(Long id) {
        o2HelpCenterRepository.deleteById(id);
    }

    @Override
    public void deleteAllOxygenHelpCenters() {
        o2HelpCenterRepository.deleteAll();
    }

    @Override
    public ResponseEntity<Object> saveReport(Report report) {
        Optional<OxygenHelpCenter> oxygenHelpCenter = o2HelpCenterRepository.findById(report.getO2_help_center_id());
        if (oxygenHelpCenter.isPresent()){
            User user = userRepository.findByEmail(report.getUser_email());
            if(user == null){
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                        .body(new ResponseMessage<Object>(false,"User doesn't exist with this email "+report.getUser_email()+" !",null));
            }else{
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseMessage<Report>(true,"Reported successfully!",reportRepository.save(report)));
            }
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage<Object>(false,"Data not exists with this id "+report.getO2_help_center_id()+" !",null));
        }
    }

    @Override
    public Report deleteReportById(Long id) {
        Optional<Report> report = reportRepository.findById(id);
        if(report.isPresent()){
            reportRepository.deleteById(id);
            return report.get();
        }
        return null;
    }

    @Override
    public void deleteAllReports() {
        reportRepository.deleteAll();
    }

    @Override
    public Report getReportById(Long id) {
        Optional<Report> report = reportRepository.findById(id);
        if(report.isPresent()){
            return report.get();
        }
        return null;
    }

    @Override
    public List<Report> getAllReport() {
        return reportRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(s);
        if(user == null){
            throw new UsernameNotFoundException("Invalid email");
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),mapRolesToAuthorities(user.getRoles()));

    }

    public Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
