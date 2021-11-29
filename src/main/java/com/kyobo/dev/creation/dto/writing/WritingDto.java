package com.kyobo.dev.creation.dto.writing;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WritingDto {

    @ApiModelProperty(value = "작품 제목", required = true, example = "title")
    private String title;

    @ApiModelProperty(value = "작품 내용", required = true)
    private String contents;

}