package com.example.cocoin.service.cocoin.database;

import com.zaxxer.hikari.HikariDataSource;
import org.example.database.common.datasource.RoutingDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.example.cocoin.service.cocoin.database.rep.jpa",
        entityManagerFactoryRef = "cocoinEntityManagerFactory",
        transactionManagerRef = "cocoinTransactionManager"
)
public class CocoinDataConfig {
    @ConfigurationProperties("database.datasource.cocoin.master")
    @Bean
    public DataSourceProperties cocoinMasterDatasourceProperties() {
        return new DataSourceProperties();
    }

    @ConfigurationProperties("database.datasource.cocoin.master.configure")
    @Bean
    public DataSource cocoinMasterDatasource() {
        return cocoinMasterDatasourceProperties()
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @ConfigurationProperties("database.datasource.cocoin.slave1")
    @Bean
    public DataSourceProperties cocoinSlave1DatasourceProperties() {
        return new DataSourceProperties();
    }

    @ConfigurationProperties("database.datasource.cocoin.slave1.configure")
    @Bean
    public DataSource cocoinSlave1Datasource() {
        return cocoinSlave1DatasourceProperties()
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean cocoinEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("cocoinMasterDatasource") DataSource masterDatasource,
            @Qualifier("cocoinSlave1Datasource") DataSource slaveDatasource
    ) {
        RoutingDataSource routingDataSource = new RoutingDataSource();
        Map<Object, Object> datasourceMap = new HashMap<Object, Object>() {
            {
                put("master", masterDatasource);
                put("slave", slaveDatasource);
            }
        };

        routingDataSource.setTargetDataSources(datasourceMap);
        routingDataSource.setDefaultTargetDataSource(masterDatasource);
        routingDataSource.afterPropertiesSet();

        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "validate");
//        properties.put("hibernate.hbm2ddl.auto", "create");
        properties.put("hibernate.default_batch_fetch_size", 1000);
        properties.put("hibernate.show_sql", false);
        properties.put("hibernate.format_sql", true);
        properties.put("hibernate.use_sql_comments", true);

        return builder
                .dataSource(new LazyConnectionDataSourceProxy(routingDataSource))
                .packages("com.example.cocoin.service.cocoin.database.rep.jpa", "org.example.database.database.auth.rep.jpa")
                .properties(properties)
                .persistenceUnit("cocoinEntityManagerFactory")
                .build();
    }

    @Bean(name = "cocoinTransactionManager")
    public PlatformTransactionManager cocoinTransactionManager(
            final @Qualifier("cocoinEntityManagerFactory") LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean
    ) {
        return new JpaTransactionManager(localContainerEntityManagerFactoryBean.getObject());
    }
}