package com.example.cocoin.service.auth.api.biz;

import com.example.cocoin.common.config.jwt.provider.JwtTokenProvider;
import com.example.cocoin.service.auth.database.rep.jpa.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.example.database.auth.database.rep.jpa.user.UserDTO;
import org.example.database.auth.database.rep.jpa.user.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

//    public UserDTO getUserInfo(String userEmail) {
//        UserEntity userEntity = userRepository.findByUserEmail(userEmail)
//                .orElseThrow(() -> new NoSuchElementException("회원이 없습니다."));
//        if (!userEntity.getUserEmail().equals(jwtTokenProvider.getCurrentUserEmail())) {
//            throw new IllegalArgumentException("회원 정보가 일치하지 않습니다.");
//        }
//        return UserDTO.builder()
//                .userEmail(userEntity.getUserEmail())
//                .userNick(userEntity.getUserNick())
//                .userAuthRole(userEntity.getRoles())
//                .build();
//    }

//    public UserDTO getUserInfo(UserDetails userDetails) {
//        UserEntity userEntity = userRepository.findByEmailWithRole(userDetails.getUsername())
//                .orElseThrow(() -> new NoSuchElementException("회원이 없습니다."));
//        return UserDTO.of(userEntity);
//    }

}
