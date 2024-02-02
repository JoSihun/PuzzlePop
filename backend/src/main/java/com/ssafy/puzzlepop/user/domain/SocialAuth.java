package com.ssafy.puzzlepop.user.domain;

import com.ssafy.puzzlepop.user.util.AuthProvider;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.util.Map;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class SocialAuth {
    private String providerId;
    @Enumerated(value = EnumType.STRING)
    private AuthProvider provider;
    private String email;
    private String name;
    private String imageUrl;
    private String attributes;
    private String ip;

    public void update(String name, String imageUrl, Map<String, Object> attributes) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.attributes = attributes.toString();
    }

}