package com.ssafy.puzzlepop.dm.domain;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DmReadResponseDto {

    private int id;
    private String fromUserId;
    private String toUserId;
    private String content;
    private Date createTime;

}
