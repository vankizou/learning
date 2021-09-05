package com.zoufanqi.transaction;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

/**
 * 事务测试
 *
 * @author: ZOUFANQI
 * @create: 2021-09-04 12:16
 **/
@Service
public class MyService {
    private final MyDao1 myDao1;
    private final MyDao2 myDao2;

    public MyService(MyDao1 myDao1, MyDao2 myDao2) {
        this.myDao1 = myDao1;
        this.myDao2 = myDao2;
    }

    /**
     * 事件回滚
     * myDao1失败
     * myDao2失败
     *
     * @throws SQLException
     */
    @Transactional
    public void requireExceptionTest1() {
        this.myDao1.require();
        this.myDao2.require();
        throw new RuntimeException("测试异常...");
    }

    /**
     * 事件回滚
     * myDao1失败
     * myDao2失败
     *
     * @throws SQLException
     */
//    @Transactional
    public void requireExceptionTest2() {
        this.myDao1.require();
        this.myDao2.requireException();
    }

    /**
     * myDao1成功
     * myDao2失败
     *
     * @throws SQLException
     */
    @Transactional
    public void requireNewExceptionTest1() {
        this.myDao1.requireNew();
        this.myDao2.requireNewException();
    }

    /**
     * myDao1成功
     * myDao2失败
     *
     * @throws SQLException
     */
    @Transactional
    public void requireNewExceptionTest2() {
        this.myDao1.requireNew();
        try {
            this.myDao2.requireNewException();
        } catch (Exception e) {
            System.out.println("这里抛了异常，但被捕获了");
        }
    }


}
