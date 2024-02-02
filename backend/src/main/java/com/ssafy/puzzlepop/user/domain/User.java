package com.ssafy.puzzlepop.user.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Date;
import java.sql.Timestamp;

//@Data
//@Entity
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @Column(length = 50)
//    private String nickname;
//    // @Column(unique = true, length = 50)
//    private String email;
//    private String password;
//
//    @Column(length = 50)
//    private String givenName;
//    @Column(length = 50)
//    private String familyName;
//    @Column(length = 50)
//    private String locale;
//    @Column(length = 1024)
//    private String imgPath;
//
//    @ColumnDefault("true")
//    private Boolean bgm;
//    @ColumnDefault("true")
//    private Boolean soundEffect;
//
//    private int playingGameID;
//    @ColumnDefault("0")
//    private int gold;
//    @Column(length = 32)
//    private String onlineStatus;
//
//    /////////////////
//    private String accessToken;
//    private String refreshToken;
//    private String tokenType;
//
//    private String provider;
//    private String providerId;
//    private String role;
//
//    private Timestamp createdDate;
//    private Timestamp expiredDate;
//
//    private SocialAuth socialAuth;
//
////    private OAuth2UserInfo oAuth2UserInfo;
////
////    public User(OAuth2UserInfo oAuth2UserInfo) {
////        this.oAuth2UserInfo = oAuth2UserInfo;
////    }
////    public User(OAuth2UserInfo oAuth2UserInfo, String role)  {
////        this.oAuth2UserInfo = oAuth2UserInfo;
////        this.role = role;
////    }
//
//    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    public void update(UserDto userDto) {
//        this.id = userDto.getId();
//        this.password = userDto.getPassword();
//        this.email = userDto.getEmail();
//        this.nickname = userDto.getNickname();
//        this.givenName = userDto.getGivenName();
//        this.familyName = userDto.getFamilyName();
//        this.imgPath = userDto.getImgPath();
//        this.locale = userDto.getLocale();
//        this.bgm = userDto.getBgm();
//        this.soundEffect = userDto.getSoundEffect();
//        this.playingGameID = userDto.getPlayingGameID();
//        this.gold = userDto.getGold();
//        this.onlineStatus = userDto.getOnlineStatus();
//        this.accessToken = userDto.getAccessToken();
//        this.refreshToken = userDto.getRefreshToken();
//        this.tokenType = userDto.getTokenType();
//        this.createdDate = userDto.getCreatedDate();
//        this.expiredDate = userDto.getExpiredDate();
//        this.role = userDto.getRole();
//        this.provider = userDto.getProvider();
//        this.providerId = userDto.getProviderId();
//    }
//}

@ToString(exclude = "socialAuth")
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
@SecondaryTables({
        @SecondaryTable(name = "social_auth", pkJoinColumns = @PrimaryKeyJoinColumn(name = "user_id"))
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private long id;

    @Column(name = "email", length = 200, nullable = false)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "nickname", length = 50, nullable = false)
    private String nickname;

    @Column(name = "tel", length = 20)
    private String tel;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false, updatable = false)
    private Date created;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated", nullable = false)
    private Date updated;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "providerId", column = @Column(table = "social_auth", name = "provider_id")),
            @AttributeOverride(name = "provider", column = @Column(table = "social_auth", name = "provider")),
            @AttributeOverride(name = "email", column = @Column(table = "social_auth", name = "email", length = 100, nullable = false)),
            @AttributeOverride(name = "name", column = @Column(table = "social_auth", name = "name", length = 100, nullable = false)),
            @AttributeOverride(name = "imageUrl", column = @Column(table = "social_auth", name = "image_url", columnDefinition = "TEXT")),
            @AttributeOverride(name = "attributes", column = @Column(table = "social_auth", name = "attributes", columnDefinition = "TEXT")),
            @AttributeOverride(name = "ip", column = @Column(table = "social_auth", name = "ip", length = 30, nullable = false)),
    })
    private SocialAuth socialAuth;

}
