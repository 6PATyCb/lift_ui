/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.java_inside.lift_ui;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 *
 * @author 6PATyCb
 */
@Configuration
@PropertySource(ignoreResourceNotFound = false, value = "classpath:application.properties")
@ComponentScan("ru.java_inside.lift_ui.*")
@Import(CoreWebAppConfig.class)
@EnableScheduling
public class MyWebAppConfig {

    public static final String PROFILE = "${spring.profiles.active:default}";
    public static final String DB_URL = "${spring.datasource.url}";
    @Value(DB_URL)
    private String dbUrl;

    @Bean
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        String dbName = dbUrl.replaceFirst("^.+:([^:]+)$", "$1");
        //  System.out.println("!!" + dbName);
        builder.setType(EmbeddedDatabaseType.H2)
                .setName(dbName)
                .setScriptEncoding("UTF-8");
        return builder.build();
    }

    @Bean
    @Order(value = SpringProxyOrder.TX_ORDER)
    public DataSourceTransactionManager txManager() {
        DataSourceTransactionManager dstm = new DataSourceTransactionManager();
        dstm.setDataSource(dataSource());
        return dstm;
    }

    @Bean()
    public JdbcTemplate jdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource());
        return jdbcTemplate;
    }
}
