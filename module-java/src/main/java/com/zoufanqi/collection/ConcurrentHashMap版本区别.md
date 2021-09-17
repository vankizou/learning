
## HashMap优化
- Hash散列优化
(key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16)
hashcode与高位异或同时保持hashcode的高低位特征，降低hash寻址的冲突概率

- Hash寻址优化
不用取模（%）运算，改用 hashcode & (n-1)

## 1.7与1.8的ConcurrentHashMap对比

| 异同点 | JDK1.7 | JDK1.8 |
| --- | --- | ---|
| 数据结构 | Segment(数组) + table(数组) + HashEntry(单链表) | table(数组) + Node(单链表) / TreeBin(红黑树)
| 锁 | ReentrantLock(Segment) + Unself | CAS + Synchronized(Node头节点) + Unself |
| 扩容 | 单线程resize | 通过ForwardNode，分步扩容（sizeCtl=-1），多线程操作时可一起参与扩容 |
| 加载因子 | 0.75f | n - (n >>> 2) == 0.75f |
| 数据迁移方式 | 头插法 | 尾插法 |
| size() | 计算2次，如果值不变返回结果；如果不一致，锁住所有Segment求和 | 分片计数：baseCount + countCell[]（多线程cas修改baseCount失败会放到该数组中） |
| get() | 通过volatile保证数据的弱一致性 | 通过volatile保证数据的弱一致性 |
| put() | 先Hash，通过Hash的高位（与ConcurrentLevel有关）定位Segment，再锁住该Segment进行put | 先Hash，找到Node，加锁，put，当获取到首节点hash=-1时表示在扩容则会协助扩容 |
| 初始化并行度 | 并行度为Segment数组大小（默认为16，初始化后不可改变） | 无实际用途 |

## 1.7版本HashMap扩容时产生死锁的问题
1.7版本数据迁移是头插法。
假如原数据：1 -> 2
线程一则会变成：2 -> 1
线程二又会变成：1 -> 2

而数据是引用的，所以产生死循环

## 面试题：如何在很短的时间内将大量数据插入到ConcurrentHashMap？
【仅供参考】

影响put性能的主要有两个因素：1. 扩容，2. 锁的竞争。可以考虑从这两方面入手提高性能。
1. 所以为了减少扩容可以根据业务适当的扩大容量以及调整加载因子。
2. 锁是对头节点加锁，所以可以hash后分组，将有hash冲突的放到同一个组里或队列中，插入数据的时候单线程插入。

## 1.8版本ConcurrentHashMap什么情况下会做扩容
1. 当put元素达到阈值时做扩容
2. 当链表转红黑树并且table数组长度<64时尝试做扩容