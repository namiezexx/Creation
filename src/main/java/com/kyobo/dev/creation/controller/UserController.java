package com.kyobo.dev.creation.controller;

import com.kyobo.dev.creation.dto.JoinDto;
import com.kyobo.dev.creation.dto.LoginDto;
import com.kyobo.dev.creation.dto.UserDto;
import com.kyobo.dev.creation.dto.response.BaseResponse;
import com.kyobo.dev.creation.dto.response.SingleDataResponse;
import com.kyobo.dev.creation.exception.DuplicatedUsernameException;
import com.kyobo.dev.creation.exception.LoginFailedException;
import com.kyobo.dev.creation.exception.UserNotFoundException;
import com.kyobo.dev.creation.service.ResponseService;
import com.kyobo.dev.creation.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"1. User"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final ResponseService responseService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @ApiOperation(value = "가입", notes = "회원가입을 한다.")
    @PostMapping("/join")
    public ResponseEntity join(@RequestBody JoinDto joinDto) {
        ResponseEntity responseEntity = null;
        try {
            UserDto savedUser = new UserDto();
            savedUser.setUser_id(joinDto.getUser_id());
            savedUser.setUser_pw(joinDto.getUser_pw());
            savedUser.setNickname(joinDto.getNickname());
            savedUser.setAge(joinDto.getAge());
            savedUser.setEmail(joinDto.getEmail());
            savedUser.setPhone(joinDto.getPhone());
            savedUser = userService.join(savedUser);
            SingleDataResponse<UserDto> response = responseService.getSingleDataResponse(true, "회원가입 성공", savedUser);

            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (DuplicatedUsernameException exception) {
            logger.debug(exception.getMessage());
            BaseResponse response = responseService.getBaseResponse(false, exception.getMessage());

            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return responseEntity;
    }

    @ApiOperation(value = "로그인", notes = "로그인을 한다.")
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDto loginDto) {
        ResponseEntity responseEntity = null;
        try {
            String token = userService.login(loginDto);

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

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원조회", notes = "가입된 회원정보를 조회한다.")
    @GetMapping("/users")
    public ResponseEntity findUserByUsername(final Authentication authentication) {
        ResponseEntity responseEntity = null;
        try {
            Long userSeq = ((UserDto) authentication.getPrincipal()).getUser_seq();
            UserDto findUser = userService.findByUserId(userSeq);

            SingleDataResponse<UserDto> response = responseService.getSingleDataResponse(true, "조회 성공", findUser);

            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (UserNotFoundException exception) {
            logger.debug(exception.getMessage());
            BaseResponse response = responseService.getBaseResponse(false, exception.getMessage());

            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return responseEntity;
    }
}