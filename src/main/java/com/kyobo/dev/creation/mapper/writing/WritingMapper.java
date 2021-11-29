package com.kyobo.dev.creation.mapper.writing;

import com.kyobo.dev.creation.dto.user.UserDto;
import com.kyobo.dev.creation.dto.writing.WritingDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface WritingMapper {
    Optional<UserDto> findByWritingSeq(Long writing_seq);
    void save(WritingDto writingDto);
}