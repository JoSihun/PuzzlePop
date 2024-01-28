package com.ssafy.puzzlepop.dm.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DmReadRequestDto {

    private String userId;
    private String friendId;

}
