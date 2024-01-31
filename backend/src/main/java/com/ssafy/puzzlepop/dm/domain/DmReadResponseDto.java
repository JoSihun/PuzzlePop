package com.ssafy.puzzlepop.dm.domain;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DmReadResponseDto {

    private Long id;
    private Long fromUserId;
    private Long toUserId;
    private String content;
    private Date createTime;

}
