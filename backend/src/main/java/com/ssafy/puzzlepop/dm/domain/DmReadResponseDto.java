package com.ssafy.puzzlepop.dm.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DmReadResponseDto {

    private Long id;
    private Long fromUserId;
    private Long toUserId;
    private String content;
    private Date createTime;

}
