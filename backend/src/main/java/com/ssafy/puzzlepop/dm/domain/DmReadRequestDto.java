package com.ssafy.puzzlepop.dm.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DmReadRequestDto {

    private Long userId;
    private Long friendId;

}
