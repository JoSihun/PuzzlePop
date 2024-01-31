package com.ssafy.puzzlepop.dm.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DmCreateDto {

    private Long fromUserId;
    private Long toUserId;
    private String content;

}
