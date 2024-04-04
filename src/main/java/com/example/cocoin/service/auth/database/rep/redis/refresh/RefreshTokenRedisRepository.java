package com.example.cocoin.service.auth.database.rep.redis.refresh;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRedisRepository extends KeyValueRepository<RefreshTokenRedis, String> {
}
