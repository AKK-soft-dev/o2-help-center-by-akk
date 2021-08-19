package com.aungkoko.o2helpcenter.model;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "report")
@NoArgsConstructor
@Getter
@Setter
@ApiModel(description = "Details of Report")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "to know user who report")
    @ApiModelProperty(value = "user email must not be null because we want to know user who report")
    private String user_email;

    @NotNull(message = "o2_help_center_id must not be null! Because it is used for reporting data")
    @ApiModelProperty(value = "o2_help_center_id must not be null! Because it is used for reporting data")
    private Long o2_help_center_id;

    @NotNull(message = "subject must not be null!")
    @ApiModelProperty(value = "subject must not be null!")
    private String subject;

    @NotNull(message = "why field must not be null cuz we want to know that why you wanna report")
    @ApiModelProperty(value = "why field must not be null cuz we want to know that why you wanna report")
    private String why;

    public Report(String  user_email, Long o2_help_center_id, String subject, String why) {
        this.user_email = user_email;
        this.o2_help_center_id = o2_help_center_id;
        this.subject = subject;
        this.why = why;
    }
}
