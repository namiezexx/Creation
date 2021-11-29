package com.kyobo.dev.creation.dto.social;

import io.swagger.annotations.ApiModel;
import lombok.*;

@ApiModel(value = "SocialDto", description = "쇼셜 유저 정보 관련 요청")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocialDto {

    private Long social_seq;

    private Long user_seq;

    private String provider;

    private String provider_uid;

    private String access_token;

    private String refresh_token;

    private String nickname;

}
