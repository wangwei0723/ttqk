package com.haozi.ttqk.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

@Configuration
@Slf4j
@MapperScan(basePackages = {"com.haozi.ttqk.mapper"},
        sqlSessionFactoryRef = "sessionFactory")
public class DataSourceConfig {


    public static final String DATA_SOURCE_PRIFIX = "spring.datasource";

    private static final ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
    @Value("${mybatis.base-packages}")
    private String typeAliasesPackage;
    @Value("${mybatis.config-location}")
    private String configLocaltion;


    @Bean
    @ConfigurationProperties(prefix = DATA_SOURCE_PRIFIX)
    public HikariDataSource dataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        return hikariDataSource;
    }

    @Bean
    public JdbcTemplate virtualCourseJdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
        return jdbcTemplate;
    }


    @Bean
    public PlatformTransactionManager virtualCourseTxManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public SqlSessionFactory sessionFactory() throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        log.info("dataSourceUrl :{}", dataSource().getJdbcUrl());
        factory.setDataSource(dataSource());
        factory.setTypeAliasesPackage(typeAliasesPackage);
        factory.setConfigLocation(resourceResolver.getResource(configLocaltion));
        factory.setMapperLocations(resourceResolver.getResources("classpath:mapper/**/*.xml"));
        SqlSessionFactory object = factory.getObject();
        object.getConfiguration().setMapUnderscoreToCamelCase(Boolean.TRUE);
        return object;
    }


}




