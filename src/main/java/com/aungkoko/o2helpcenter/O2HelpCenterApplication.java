package com.aungkoko.o2helpcenter;

import com.aungkoko.o2helpcenter.controller.dto.UserRegistrationDto;
import com.aungkoko.o2helpcenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class O2HelpCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(O2HelpCenterApplication.class, args);
    }

}
