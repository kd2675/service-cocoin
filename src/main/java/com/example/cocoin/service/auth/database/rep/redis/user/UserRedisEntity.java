package com.example.cocoin.service.auth.database.rep.redis.user;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@Getter
@RedisHash(value = "userRedis", timeToLive = 30)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRedisEntity {

    @Id
    private String id;
    private String name;
    private Integer age;
    private LocalDateTime createdAt;

}