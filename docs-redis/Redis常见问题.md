
## Redis为什么快
1. 基于内存
2. 使用IO多路复用网络模型
3. 自定义简单协议
4. 对数据结构底层做了很多优化

## Redis数据结构底层实现

| type | encoding | 对象 | 条件 |
| --- | --- | --- | --- |
| REDIS_STRING | REDIS_ENCODING_INT | 使用整形值实现的字符串对象 | 整型数据 |
| REDIS_STRING | REDIS_ENCODING_EMBSTR | 使用embstr编码的简单动态字符串实现的字符串对象 | |
| REDIS_STRING | REDIS_ENCODING_RAW | 使用简单动态字符串实现的字符串对象 | |
| REDIS_LIST | REDIS_ENCODING_ZIPLIST | 使用压缩列表实现的列表对象 | 列表长度小于512，并且所有元素长度都小于64bit，否则使用linkedlist |
| REDIS_LIST | REDIS_ENCODING_LINKEDLIST | 使用双端链表的列表对象 | |
| REDIS_HASH | REDIS_ENCODING_ZIPLIST | 使用压缩列表实现的哈希对象 | 键值对小512，并且所有键值对的长度都小于64bit，否则使用hash |
| REDIS_HASH | REDIS_ENCODING_HT | 使用字典实现的哈希对象 | |
| REDIS_SET | REDIS_ENCODING_INTSET | 使用证书集合实现的集合对象 | 值可以转成整型，并且长度不超过512，否则用hash，有三种：INTSET_ENC_INT16、INTSET_ENC_INT32、INTSET_ENC_INT64 |
| REDIS_SET | REDIS_ENCODING_HT | 使用字典实现的集合对象 | |
| REDIS_ZSET | REDIS_ENCODING_ZIPLIST | 使用压缩列表实现的有序集合对象 | 当长度小于128，并且所有元素的度都小于64bit，否则使用skiplist |
| REDIS_ZSET | REDIS_ENCODING_SKIPLIST | 使用跳跃表和字典实现的有序集合对象 | |

## 缓存清理策略
随机清理 + 惰性清除

## 缓存淘汰机制
1. 抛异常
2. 删除最早的
3. 随机删除
4. LRU（最近最少使用）

## Redis性能优化
1. 缩短键值对的存储长度；
2. 使用 lazy free（延迟删除）特性；
3. 设置键值的过期时间；
4. 禁用长耗时的查询命令；
5. 使用 slowlog 优化耗时命令；
6. 使用 Pipeline 批量操作数据；
7. 避免大量数据同时失效；
8. 客户端使用优化；
9. 限制 Redis 内存大小；
10. 使用物理机而非虚拟机安装 Redis 服务；
11. 检查数据持久化策略；
12. 禁用 THP 特性；
13. 使用分布式架构来增加读写速度。
