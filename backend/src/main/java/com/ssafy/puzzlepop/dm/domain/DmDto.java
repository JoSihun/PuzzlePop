package com.ssafy.puzzlepop.dm.domain;

import com.ssafy.puzzlepop.image.domain.Image;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DmDto {

    private int id;
    private String fromUserId;
    private String toUserId;
    private String content;
    private Date createTime;
    private Date updateTime;

    @Builder
    public DmDto(Dm dm) {
        this.id = dm.getId();
        this.fromUserId = dm.getFromUserId();
        this.toUserId = dm.getToUserId();
        this.content = dm.getContent();
        this.createTime = dm.getCreateTime();
        this.updateTime = dm.getUpdateTime();
    }

    public Dm toEntity() {
        return Dm.builder()
                .id(this.id)
                .fromUserId(this.fromUserId)
                .toUserId(this.toUserId)
                .content(this.content)
                .createTime(this.createTime)
                .updateTime(this.updateTime)
                .build();
    }

}
