package com.example.cocoin.service.auth.database.rep.redis.logout;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;


@Getter
@RedisHash("logoutAccessToken")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogoutAccessTokenRedis {
    @Id
    private String id;

    private String userEmail;

    @TimeToLive
    private Long expiration;

    public static LogoutAccessTokenRedis of(String accessToken, String userEmail, Long remainingMilliSeconds) {
        return LogoutAccessTokenRedis.builder()
                .id(accessToken)
                .userEmail(userEmail)
                .expiration(remainingMilliSeconds / 1000)
                .build();
    }
}
