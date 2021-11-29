package com.kyobo.dev.creation.mapper.social;

import com.kyobo.dev.creation.dto.social.SocialDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface SocialMapper {
    Optional<SocialDto> findByProviderUid(String provider, String provider_uid);
    void save(SocialDto socialDto);
}