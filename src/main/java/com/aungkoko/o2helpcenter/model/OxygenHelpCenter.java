package com.aungkoko.o2helpcenter.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "oxygen_help_centers")
@NoArgsConstructor
@Getter
@Setter
@ApiModel(description = "Details of oxygen help center")
public class OxygenHelpCenter {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull(message = "name or organization must not be null!")
    @ApiModelProperty(value = "name or organization must not be null!")
    private String nameOrOrganization;

    @NotNull(message = "phone number must not be null!")
    @ApiModelProperty(value = "phone number must not be null!")
    private String phoneNumber;

    @NotNull(message = "address must not be null!")
    @ApiModelProperty(value = "address must not be null!")
    private String address;

    private boolean isVerified; // this is responsible for admin team

    @NotNull(message = "data must be confirmed!")
    @ApiModelProperty(value = "data must be confirmed!")
    private boolean isStillAvailable;

    private String uploadedDate = new SimpleDateFormat("dd/MM/yy_hh:mm:ss").format(new Date());

    public OxygenHelpCenter(String nameOrOrganization, String phoneNumber, String address, boolean isStillAvailable) {
        this.nameOrOrganization = nameOrOrganization;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.isStillAvailable = isStillAvailable;
    }
}
