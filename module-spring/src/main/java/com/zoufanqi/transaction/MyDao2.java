package com.zoufanqi.transaction;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: ZOUFANQI
 * @create: 2021-09-04 09:55
 **/
@Repository
public class MyDao2 {
    public final JdbcTemplate jdbcTemplate;

    public MyDao2(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void require() {
        System.out.println("更新记录2：" + this.jdbcTemplate.update("update t1 set a=a+1 where id=2"));
    }

    @Transactional
    public void requireException() {
        System.out.println("更新记录1：" + this.jdbcTemplate.update("update t1 set a=a+1 where id=2"));
        throw new RuntimeException("test exception...");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void requireNewException() {
        System.out.println("更新记录1：" + this.jdbcTemplate.update("update t1 set a=a+1 where id=2"));
        throw new RuntimeException("test exception...");
    }
}
