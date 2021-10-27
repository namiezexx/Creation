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
public class JoinDto {

    @ApiModelProperty(value = "유저의 고유한 id값", required = true, example = "jslee")
    private String user_id;

    @ApiModelProperty(value = "유저의 고유한 password값", required = true, example = "love1203")
    private String user_pw;

    @ApiModelProperty(value = "별명", required = true, example = "namiezexx")
    private String nickname;

    @ApiModelProperty(value = "나이", required = true, example = "41")
    private int age;

    @ApiModelProperty(value = "이메일", required = true, example = "namiezexx@naver.com")
    private String email;

    @ApiModelProperty(value = "휴대폰번호", required = true, example = "01035221767")
    private String phone;
}