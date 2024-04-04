package com.example.cocoin.service.auth.database.rep.redis.refresh;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;


@Getter
@RedisHash("refreshToken")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshTokenRedis {
    @Id
    private String id;

    private String refreshToken;

    @TimeToLive
    private Long expiration;

    public static RefreshTokenRedis createRefreshToken(String userEmail, String refreshToken, Long remainingMilliSeconds) {
        return RefreshTokenRedis.builder()
                .id(userEmail)
                .refreshToken(refreshToken)
                .expiration(remainingMilliSeconds / 1000)
                .build();
    }
}
