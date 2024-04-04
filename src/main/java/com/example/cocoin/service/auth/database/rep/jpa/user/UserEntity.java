package com.example.cocoin.service.auth.database.rep.jpa.user;

import com.example.cocoin.service.auth.api.dto.JoinParamDTO;
import com.example.cocoin.service.auth.database.rep.jpa.auth.AuthEntity;
import com.example.cocoin.service.auth.database.rep.jpa.wallet.WalletEntity;
import com.example.cocoin.common.database.rep.jpa.CommonDateEntity;
import com.example.cocoin.common.utils.HttpUtils;
import com.example.cocoin.common.utils.RequestUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Entity
@Table(name = "PUB_USER_TB")
public class UserEntity extends CommonDateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "oauth2_id", unique = true)
    @ColumnDefault("''")
    private String oAuth2Id;

    @Column(name = "email", nullable = false, length = 45, unique = true)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password", nullable = false, length = 300)
    private String password;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "phone", nullable = false, length = 30)
    private String phone;

    @Column(name = "nick", nullable = false, length = 30)
    private String nick;

    @Column(name = "ip", nullable = false, length = 60)
    private String ip;

    @ColumnDefault("'n'")
    @Column(name = "block", columnDefinition = "char", length = 1)
    private String block;

    @OneToOne(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private WalletEntity walletEntity;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<AuthEntity> role = new HashSet<>();

    public void setWalletEntity(final WalletEntity walletEntity) {
        this.walletEntity = walletEntity;
    }

    public static UserEntity ofUser(JoinParamDTO joinParamDTO) {
        UserEntity userEntity = null;
        HttpServletRequest request = HttpUtils.getRequest();
        try {
            userEntity = UserEntity.builder()
                    .email(joinParamDTO.getUserEmail())
                    .password(joinParamDTO.getUserPassword())
                    .name(joinParamDTO.getUserName())
                    .phone(joinParamDTO.getUserPhone())
                    .nick(joinParamDTO.getUserNick())
//                    .ip(Inet4Address.getLocalHost().getHostAddress())
                    .ip(RequestUtils.getClientIP())
//                    .block("N")
                    .build();
            userEntity.setWalletEntity(WalletEntity.builder().point(Long.valueOf(10000)).userEntity(userEntity).build());
            userEntity.addAuth(AuthEntity.ofUser(userEntity));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return userEntity;
    }

    public static UserEntity ofSocial(JoinParamDTO joinParamDTO) {
        UserEntity userEntity = null;
        HttpServletRequest request = HttpUtils.getRequest();
        try {
            userEntity = UserEntity.builder()
                    .email(joinParamDTO.getUserEmail())
                    .password(joinParamDTO.getUserPassword())
                    .name(joinParamDTO.getUserName())
                    .phone(joinParamDTO.getUserPhone())
                    .nick(joinParamDTO.getUserNick())
//                    .ip(Inet4Address.getLocalHost().getHostAddress())
                    .ip(RequestUtils.getClientIP())
//                    .block("N")
                    .build();
            userEntity.setWalletEntity(WalletEntity.builder().point(Long.valueOf(10000)).userEntity(userEntity).build());
            userEntity.addAuth(AuthEntity.ofUser(userEntity));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return userEntity;
    }

    public static UserEntity ofAdmin(JoinParamDTO joinParamDTO) {
        UserEntity userEntity = null;
        try {
            userEntity = UserEntity.builder()
                    .email(joinParamDTO.getUserEmail())
                    .password(joinParamDTO.getUserPassword())
                    .name(joinParamDTO.getUserName())
                    .phone(joinParamDTO.getUserPhone())
                    .nick(joinParamDTO.getUserNick())
                    .ip(Inet4Address.getLocalHost().getHostAddress())
//                    .block("N")
                    .build();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        userEntity.setWalletEntity(WalletEntity.builder().point(Long.valueOf(10000)).userEntity(userEntity).build());
        userEntity.addAuth(AuthEntity.ofAdmin(userEntity));
        return userEntity;
    }

    private void addAuth(AuthEntity userAuthEntity) {
        role.add(userAuthEntity);
    }

    public List<String> getRoles() {
        return role.stream()
                .map(AuthEntity::getRole)
                .collect(Collectors.toList());
    }

}
