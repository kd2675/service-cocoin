package com.example.cocoin.common.config;

import com.example.cocoin.common.config.jwt.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Optional;

@Configuration
@EnableJpaAuditing // JPA Auditing 활성화
@RequiredArgsConstructor
public class JpaConfig implements AuditorAware<String> {
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }
    @Bean
    public EntityManagerFactoryBuilder entityManagerFactoryBuilder() {
        return new EntityManagerFactoryBuilder(new HibernateJpaVendorAdapter(), new HashMap<>(), null);
    }

    @Override
    public Optional<String> getCurrentAuditor() {
        try {
            String currentUserEmail = jwtTokenProvider.getUserEmail();
            return Optional.of(currentUserEmail);
        } catch (ClassCastException e) {
            return Optional.empty();
        }
    }
}
