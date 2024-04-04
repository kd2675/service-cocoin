package com.example.cocoin.service.auth.api.biz;

import com.example.cocoin.service.auth.api.dto.TokenDTO;
import com.example.cocoin.service.auth.database.rep.redis.logout.LogoutAccessTokenRedis;
import com.example.cocoin.service.auth.database.rep.redis.logout.LogoutAccessTokenRedisRepository;
import com.example.cocoin.service.auth.database.rep.redis.refresh.RefreshTokenRedisRepository;
import com.example.cocoin.common.config.jwt.cache.CacheKey;
import com.example.cocoin.common.config.jwt.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogoutService {
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;
    private final LogoutAccessTokenRedisRepository logoutAccessTokenRedisRepository;

    // 4
    @Transactional
    @CacheEvict(value = CacheKey.USER, key = "#p0")
    public void logout(String email, TokenDTO tokenDTO) {
        String accessToken = jwtTokenProvider.resolveToken(tokenDTO.getAccessToken());
        String refreshToken = jwtTokenProvider.resolveToken(tokenDTO.getRefreshToken());
        long remainMilliSeconds = jwtTokenProvider.getRemainMilliSeconds(refreshToken);
        refreshTokenRedisRepository.deleteById(email);
        logoutAccessTokenRedisRepository.save(LogoutAccessTokenRedis.of(accessToken, email, remainMilliSeconds));
    }

}
