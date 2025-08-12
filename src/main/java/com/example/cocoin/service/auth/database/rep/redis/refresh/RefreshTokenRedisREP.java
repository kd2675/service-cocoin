package com.example.cocoin.service.auth.database.rep.redis.refresh;

import org.example.database.database.auth.redis.RefreshTokenRedis;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRedisREP extends KeyValueRepository<RefreshTokenRedis, String> {
}
