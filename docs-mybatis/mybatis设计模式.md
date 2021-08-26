## mybatis中使用的设计模式

## 工厂设计模式
- 使用的地方举例
DataSourceFactory
UnpoolDataSourceFactory
PoolDataSourceFactory

内置池化技术

## 建造者设计模式
- 使用的地方举例
配置管理
BaseBuilder: 所有解析器的父类，包含配置文件实例，为解析文件提供的一些通用的方法;
XMLConfigBuilder: 主要负责解析mybatis-config.xml;
XMLMapperBuilder: 主要负责解析映射配置Mapper.xml文件;
XMLStatementBuilder: 主要负责解析映射配置文件中的SQL节点;



## 适配器设计模式
- 使用的地方举例
日志模块：slf4j -> commonsLoging -> log4j2 -> log4j -> jdklog

检测到哪个有类实现自动适配对应的日志模块

### 外观（门面）设计模式
- 使用的地方举例
SqlSessionFactory
SqlSession

对使用者来说，统一入口

## 装饰器设计模式
- 使用的地方举例
缓存模块
Cache: Cache接口是缓存模块的核心接口，定义了缓存的基本操作; 
PerpetualCache: 在缓存模块中扮演 ConcreteComponent 角色，使用HashMap 来实现 cache 的相关操作;
BlockingCache: 阻塞版本的缓存装饰器，保证只有一个线程到数据库去查找指定的 key 对应的数据;

## 动态代理设计模式
- 使用的地方举例
日志模块
mapper

通过动态代理对其他模块无侵入的添加日志收集功能，如：打印执行sql、参数、缓存命中等



## 模版方法设计模式
- 使用的地方举例
数据访问主要的组件：Executor

## 策略设计模式
- 使用的地方举例
连接池，实现方式有多种，可以自己选择，如：POOL、UNPOOL。策略模式其实很多地方都用到




