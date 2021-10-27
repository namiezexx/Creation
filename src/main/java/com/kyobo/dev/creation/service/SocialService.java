package com.kyobo.dev.creation.service;

import com.kyobo.dev.creation.dto.LoginDto;
import com.kyobo.dev.creation.dto.SocialDto;
import com.kyobo.dev.creation.dto.UserDto;
import com.kyobo.dev.creation.exception.DuplicatedUsernameException;
import com.kyobo.dev.creation.exception.LoginFailedException;
import com.kyobo.dev.creation.exception.UserNotFoundException;
import com.kyobo.dev.creation.mapper.SocialMapper;
import com.kyobo.dev.creation.mapper.UserMapper;
import com.kyobo.dev.creation.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SocialService {

    private final SocialMapper socialMapper;
    private final UserMapper userMapper;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public SocialDto join(SocialDto socialDto) {
        if (socialMapper.findByProviderUid(socialDto.getProvider(), socialDto.getProvider_uid()).isPresent()) {
            throw new DuplicatedUsernameException("이미 가입된 유저입니다");
        }

        socialMapper.save(socialDto);

        return socialMapper.findByProviderUid(socialDto.getProvider(), socialDto.getProvider_uid()).get();
    }

    public String login(SocialDto socialDto) {
        socialDto = socialMapper.findByProviderUid(socialDto.getProvider(), socialDto.getProvider_uid())
                .orElseThrow(() -> new LoginFailedException("잘못된 아이디입니다"));

        UserDto userDto = userMapper.findByUserSeq(socialDto.getUser_seq())
                .orElseThrow(() -> new LoginFailedException("잘못된 아이디입니다"));

        return jwtTokenProvider.createToken(userDto.getUser_seq(), Collections.singletonList(userDto.getRole()));
    }

    public UserDto findByUserId(Long user_seq) {
        return userMapper.findByUserSeq(user_seq)
                .orElseThrow(() -> new UserNotFoundException("없는 유저입니다."));
    }
}