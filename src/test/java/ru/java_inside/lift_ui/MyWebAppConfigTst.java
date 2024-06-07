/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.java_inside.lift_ui;

import java.time.Clock;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
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
@EnableScheduling
public class MyWebAppConfigTst {

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        builder.setType(EmbeddedDatabaseType.H2)
                .setName("forTest")
                .setScriptEncoding("UTF-8")
                .addScript("classpath:schema.sql")
                .addScript("classpath:data.sql");
        return builder.build();
    }

    @Bean
    @Order(value = 2)
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
