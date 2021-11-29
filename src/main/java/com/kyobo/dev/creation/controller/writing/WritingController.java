package com.kyobo.dev.creation.controller.writing;

import com.kyobo.dev.creation.controller.user.UserController;
import com.kyobo.dev.creation.dto.writing.WritingDto;
import com.kyobo.dev.creation.service.ResponseService;
import com.kyobo.dev.creation.service.user.UserService;
import com.kyobo.dev.creation.service.writing.WritingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"3. Writing"})
@Log
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class WritingController {

    private final UserService userService;
    private final WritingService writingService;
    private final ResponseService responseService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "글쓰기", notes = "작품등록을 한다.")
    @PostMapping("/writing")
    public ResponseEntity save(final Authentication authentication,
                               @RequestBody WritingDto writingDto) {

        ResponseEntity responseEntity = null;

        writingService.save(writingDto);

        responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(null);

        return responseEntity;
    }

}
