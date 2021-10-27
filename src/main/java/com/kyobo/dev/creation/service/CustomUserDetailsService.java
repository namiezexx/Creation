package com.kyobo.dev.creation.service;

import com.kyobo.dev.creation.dto.UserDto;
import com.kyobo.dev.creation.exception.UserNotFoundException;
import com.kyobo.dev.creation.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String userSeq) throws UsernameNotFoundException {
        return userMapper.findByUserSeq(Long.valueOf(userSeq))
                .map(user -> addAuthorities(user))
                .orElseThrow(() -> new UserNotFoundException(userSeq + "> 찾을 수 없습니다."));
    }

    private UserDto addAuthorities(UserDto userDto) {
        userDto.setAuthorities(Arrays.asList(new SimpleGrantedAuthority(userDto.getRole())));

        return userDto;
    }
}