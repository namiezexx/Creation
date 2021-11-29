package com.kyobo.dev.creation.mapper.user;

import com.kyobo.dev.creation.dto.user.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {
    Optional<UserDto> findByUserId(String user_id);
    Optional<UserDto> findByUserSeq(Long user_seq);
    void save(UserDto userDto);
}