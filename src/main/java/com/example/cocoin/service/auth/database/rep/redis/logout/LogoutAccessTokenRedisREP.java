package com.example.cocoin.service.auth.database.rep.redis.logout;

import org.example.database.database.auth.redis.LogoutAccessTokenRedis;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogoutAccessTokenRedisREP extends KeyValueRepository<LogoutAccessTokenRedis, String> {
}