package com.kyobo.dev.creation.service.user;

import com.kyobo.dev.creation.dto.user.LoginDto;
import com.kyobo.dev.creation.dto.user.UserDto;
import com.kyobo.dev.creation.exception.DuplicatedUsernameException;
import com.kyobo.dev.creation.exception.LoginFailedException;
import com.kyobo.dev.creation.exception.UserNotFoundException;
import com.kyobo.dev.creation.mapper.user.UserMapper;
import com.kyobo.dev.creation.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserMapper userMapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDto join(UserDto userDto) {
        if (userMapper.findByUserId(userDto.getUser_id()).isPresent()) {
            throw new DuplicatedUsernameException("이미 가입된 유저입니다");
        }

        userDto.setUser_pw(passwordEncoder.encode(userDto.getPassword()));
        userMapper.save(userDto);

        return userMapper.findByUserId(userDto.getUser_id()).get();
    }

    public String login(LoginDto loginDto) {
        UserDto userDto = userMapper.findByUserId(loginDto.getUser_id())
                .orElseThrow(() -> new LoginFailedException("잘못된 아이디입니다"));

        if (!passwordEncoder.matches(loginDto.getUser_pw(), userDto.getPassword())) {
            throw new LoginFailedException("잘못된 비밀번호입니다");
        }

        return jwtTokenProvider.createToken(userDto.getUser_seq(), Collections.singletonList(userDto.getRole()));
    }

    public UserDto findByUserId(Long user_seq) {
        return userMapper.findByUserSeq(user_seq)
                .orElseThrow(() -> new UserNotFoundException("없는 유저입니다."));
    }
}