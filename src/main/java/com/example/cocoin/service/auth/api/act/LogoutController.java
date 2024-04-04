package com.example.cocoin.service.auth.api.act;

import com.example.cocoin.service.auth.api.biz.LogoutService;
import com.example.cocoin.service.auth.api.dto.TokenDTO;
import com.example.cocoin.common.base.dto.ResponseDTO;
import com.example.cocoin.common.base.vo.Code;
import com.example.cocoin.common.config.jwt.provider.JwtTokenProvider;
import com.example.cocoin.common.utils.CookieUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"/api/auth"})
public class LogoutController {
    private final LogoutService logoutService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/logout")
    public ResponseDTO logout(
            @RequestHeader("Authorization") String accessToken,
            @CookieValue("RefreshToken") String refreshToken
    ) {
        CookieUtils.deleteCookie("RefreshToken");
        String userEmail = jwtTokenProvider.getUserEmail(jwtTokenProvider.resolveToken(accessToken));
        logoutService.logout(userEmail, TokenDTO.of(accessToken, refreshToken));

        return ResponseDTO.of(true, Code.OK);
    }
}
