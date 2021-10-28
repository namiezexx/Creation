package com.kyobo.dev.creation.controller;

import com.kyobo.dev.creation.dto.*;
import com.kyobo.dev.creation.dto.response.BaseResponse;
import com.kyobo.dev.creation.dto.response.SingleDataResponse;
import com.kyobo.dev.creation.exception.DuplicatedUsernameException;
import com.kyobo.dev.creation.exception.LoginFailedException;
import com.kyobo.dev.creation.exception.UserNotFoundException;
import com.kyobo.dev.creation.service.KakaoService;
import com.kyobo.dev.creation.service.ResponseService;
import com.kyobo.dev.creation.service.SocialService;
import com.kyobo.dev.creation.service.UserService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"2. Social Login"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SocialController {

    private final UserService userService;
    private final SocialService socialService;
    private final KakaoService kakaoService;
    private final ResponseService responseService;
    private final Logger logger = LoggerFactory.getLogger(SocialController.class);

    @ApiOperation(value = "소셜계정가입", notes = "소셜계정 회원가입을 한다.")
    @PostMapping("/join/{provider}")
    public ResponseEntity join(@ApiParam(value = "서비스 제공자 provider", required = true, defaultValue = "kakao") @PathVariable String provider,
                               @ApiParam(value = "소셜 access_token", required = true) @RequestParam String accessToken,
                               @ApiParam(value = "소셜 refresh_token", required = true) @RequestParam String refreshToken) {

        ResponseEntity responseEntity = null;

        try {
            KakaoProfile profile = kakaoService.getKakaoProfile(accessToken);

            UserDto savedUser = new UserDto();
            savedUser.setUser_id(Long.toString(profile.getId()));
            savedUser.setUser_pw("1234");
            savedUser = userService.join(savedUser);

            SocialDto socialDto = new SocialDto();
            socialDto.setProvider(provider);
            socialDto.setProvider_uid(Long.toString(profile.getId()));
            socialDto.setAccess_token(accessToken);
            socialDto.setRefresh_token(refreshToken);
            socialDto.setUser_seq(savedUser.getUser_seq());
            socialDto = socialService.join(socialDto);

            SingleDataResponse<UserDto> response = responseService.getSingleDataResponse(true, "회원가입 성공", savedUser);

            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception exception) {
            logger.debug(exception.getMessage());
            BaseResponse response = responseService.getBaseResponse(false, exception.getMessage());

            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return responseEntity;
    }

    @ApiOperation(value = "소셜계정 로그인", notes = "소셜계정 로그인을 한다.")
    @PostMapping("/login/{provider}")
    public ResponseEntity login(@ApiParam(value = "서비스 제공자 provider", required = true, defaultValue = "kakao") @PathVariable String provider,
                                @ApiParam(value = "소셜 access_token", required = true) @RequestParam String accessToken) {

        ResponseEntity responseEntity = null;

        try {

            KakaoProfile profile = kakaoService.getKakaoProfile(accessToken);

            SocialDto socialDto = new SocialDto();
            socialDto.setProvider(provider);
            socialDto.setProvider_uid(Long.toString(profile.getId()));
            String token = socialService.login(socialDto);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Authorization", "Bearer " + token);

            SingleDataResponse<String> response = responseService.getSingleDataResponse(true, "로그인 성공", token);

            responseEntity = ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(response);
        } catch (LoginFailedException exception) {
            logger.debug(exception.getMessage());
            BaseResponse response = responseService.getBaseResponse(false, exception.getMessage());

            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return responseEntity;
    }

}