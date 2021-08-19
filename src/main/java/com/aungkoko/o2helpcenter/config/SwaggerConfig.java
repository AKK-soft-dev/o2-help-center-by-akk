package com.aungkoko.o2helpcenter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

//Configuration
@Configuration
//Enable Swagger
@EnableSwagger2
public class SwaggerConfig
{
    //configuring the contact detail
    public static final Contact DEFAULT_CONTACT = new Contact("Aung Ko Ko", "http://www.blahblah.com", "aungkokosoftdev@gmail.com");
    //configuring DEFAULT_API_INFO
    public static final ApiInfo DEFAULT_API_INFO = new ApiInfo("Oxygen Help Centers Api", "Rest Api for clients who want to help people need oxygen", "1.0", "urn:tos",
            DEFAULT_CONTACT, "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0");
    //format which we want to produce and consume
    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<String>(Arrays.asList("application/json"));
    //creating bean
    @Bean
    public Docket api()
    {
        ApiInfo apiInfo;
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(DEFAULT_API_INFO).produces(DEFAULT_PRODUCES_AND_CONSUMES).consumes(DEFAULT_PRODUCES_AND_CONSUMES);
    }
}
