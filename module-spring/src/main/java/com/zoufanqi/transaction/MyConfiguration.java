package com.zoufanqi.transaction;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * 连接池
 *
 * @author: ZOUFANQI
 * @create: 2021-09-04 09:49
 **/
@Configuration
@EnableTransactionManagement
public class MyConfiguration {
    /**
     * 连接池配置
     *
     * @return
     */
    @Bean
    public DruidDataSource dataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl("jdbc:mysql://127.0.0.1:3306/test1");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("zoufanqi");
        return druidDataSource;
    }

    /**
     * 操作模版
     *
     * @param dataSource
     * @return
     */
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    /**
     * 事务配置
     *
     * @param dataSource
     * @return
     */
    @Bean
    public PlatformTransactionManager tm(DataSource dataSource) {
        DataSourceTransactionManager tm = new DataSourceTransactionManager();
        tm.setDataSource(dataSource);
        return tm;
    }
}
