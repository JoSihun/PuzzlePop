package com.ssafy.puzzlepop.dm.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DmCreateDto {

    private String fromUserId;
    private String toUserId;
    private String content;

}
