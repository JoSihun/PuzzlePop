package com.ssafy.puzzlepop.dm.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Dm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String fromUserId;

    @NotNull
    private String toUserId;

    @NotNull
    private String content;

    @CreationTimestamp
    @Column(nullable = false)
    private Date createTime;

    @CreationTimestamp
    @Column(nullable = false)
    private Date updateTime;
}
