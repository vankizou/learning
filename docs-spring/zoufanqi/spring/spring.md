# Bean生命周期
https://blog.csdn.net/kykangyuky/article/details/123114227

# Bean循环依赖
https://blog.csdn.net/cristianoxm/article/details/113246104

# 造成事务失效的情况
1. service没有托管给spring
2. 抛出受检异常
3. 业务自己捕获了异常
4. 切面顺序导致
5. 非public方法
    具体步骤：
    1. 在pom引入aspectjrt坐标以及相应插件
    2. 在启动类上加上如下配置
    3. 直接用TransactionTemplate
6. 父子容器
7. 方法用final修饰
8. 方法用static修饰
9. 调用本类方法
10. 多线程调用
11. 错误的传播行为
12. 使用了不支持事务的存储引擎
13. 数据源没有配置事务管理器
14. 被代理的类过早实例化

https://blog.csdn.net/xubenxismile/article/details/123591394