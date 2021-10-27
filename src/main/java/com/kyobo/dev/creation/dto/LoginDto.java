package com.kyobo.dev.creation.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

    @ApiModelProperty(value = "유저의 고유한 id값", required = true)
    private String user_id;

    @ApiModelProperty(value = "유저의 고유한 password값", required = true)
    private String user_pw;
}