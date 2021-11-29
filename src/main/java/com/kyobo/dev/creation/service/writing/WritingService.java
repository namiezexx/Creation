package com.kyobo.dev.creation.service.writing;

import com.kyobo.dev.creation.dto.user.LoginDto;
import com.kyobo.dev.creation.dto.user.UserDto;
import com.kyobo.dev.creation.dto.writing.WritingDto;
import com.kyobo.dev.creation.exception.DuplicatedUsernameException;
import com.kyobo.dev.creation.exception.LoginFailedException;
import com.kyobo.dev.creation.exception.UserNotFoundException;
import com.kyobo.dev.creation.mapper.user.UserMapper;
import com.kyobo.dev.creation.mapper.writing.WritingMapper;
import com.kyobo.dev.creation.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WritingService {

    private final WritingMapper writingMapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void save(WritingDto writingDto) {

        writingMapper.save(writingDto);

        return ;
    }

}