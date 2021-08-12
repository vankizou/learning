## 常用的阻塞队列
- ArrayBlockingQueue: 一个由数组结构组成的有界阻塞队列
- LinkedBlockingQueue: 一个由链表结构组成的有界阻塞队列
- PriorityBlockingQueue: 一个支持优先级排序的无界阻塞队列
- DelayQueue: 一个使用优先级队列实现的无界阻塞队列
- SynchronousQueue: 一个不存储元素的阻塞队列
- LinkedTransferQueue: 一个由链表结构组成的无界阻塞队列
- LinkedBlockingDeque: 一个由链表结构组成的双向阻塞队列

## 有界无界？
有限队列就是长度有限，满了以后生产者会阻塞，空了以后消费者也会阻塞，无界队列就是里面能放无数的东西而不会因为队列长度限制被阻塞。
当然空间限制来源于系统资源的限制，如果处理不及时，导致队列越来越大，超出一定的限制致使内存超限。

无界也会阻塞？因为阻塞不仅仅体现在生产者放入元素时会阻塞，消费者拿取元素时，如果没有元素，同样也会阻塞。

## ArrayBlockingQueue
是一个用数组实现的有界阻塞队列。此队列按照先进先出（FIFO）的原则对元素进行排序。
默认情况下不保证线程公平的访问队列，所谓公平访问队列是指阻塞的线程，可以按照阻塞的先后顺序访问队列，即先阻塞线程先访问队列。
非公平性是对先等待的线程是非公平的，当队列可用时，阻塞的线程都可以争夺访问队列的资格，有可能先阻塞的线程最后才访问队列。
初始化时有参数可以设置。
 
## LinkedBlockingQueue
是一个用链表实现的有界阻塞队列。
此队列的默认和最大长度为Integer.MAX_VALUE。
此队列按照先进先出的原则对元素进行排序。

## Array 实现和 Linked 实现的区别
1. 队列中锁的实现不同
ArrayBlockingQueue 实现的队列中的锁是没有分离的，即生产和消费用的是同一个锁；
LinkedBlockingQueue 实现的队列中的锁是分离的， 即生产用的是putLock，消费是takeLock

2. 在生产或消费时操作不同
ArrayBlockingQueue 实现的队列中在生产和消费的时候，是直接将枚举对象插入或移除的；
LinkedBlockingQueue 实现的队列中在生产和消费的时候，需要把枚举对象转换为Node<E>进行插入或移除，会影响性能

3. 队列大小初始化方式不同
ArrayBlockingQueue 实现的队列中必须指定队列的大小；
LinkedBlockingQueue 实现的队列中可以不指定队列的大小，
但是默认是Integer.MAX_VALUE

## PriorityBlockingQueue
PriorityBlockingQueue 是一个支持优先级的无界阻塞队列。默认情况下元素采取自然顺序升序排列。
也可以自定义类实现compareTo()方法来指定元素排序规则，或者初始化PriorityBlockingQueue时，指定构造参数Comparator来对元素进行排序。
需要注意的是不能保证同优先级元素的顺序。

## DelayQueue 
是一个支持延时获取元素的无界阻塞队列。
队列使用PriorityQueue来实现。
队列中的元素必须实现Delayed接口，在创建元素时可以指定多久才能从队列中获取当前元素。
只有在延迟期满时才能从队列中提取元素。
DelayQueue非常有用，可以将DelayQueue运用在以下应用场景。
缓存系统的设计：可以用DelayQueue保存缓存元素的有效期，使用一个线程循环查询DelayQueue，一旦能从DelayQueue中获取元素时，表示缓存有效期到了。还有订单到期，限时支付等等

## SynchronousQueue
是一个不存储元素的阻塞队列。
每一个put操作必须等待一个take操作，否则不能继续添加元素。
SynchronousQueue可以看成是一个传球手，负责把生产者线程处理的数据直接传递给消费者线程。
队列本身并不存储任何元素，非常适合传递性场景。
SynchronousQueue的吞吐量高于LinkedBlockingQueue和ArrayBlockingQueue。

## LinkedTransferQueue
多了tryTransfer和transfer方法，
1. transfer方法
如果当前有消费者正在等待接收元素（消费者使用take()方法或带时间限制的poll()方法时），transfer方法可以把生产者传入的元素立刻transfer（传输）给消费者。
如果没有消费者在等待接收元素，transfer方法会将元素存放在队列的tail节点，并等到该元素被消费者消费了才返回。

2. tryTransfer
方法tryTransfer方法是用来试探生产者传入的元素是否能直接传给消费者。
如果没有消费者等待接收元素，则返回false。
和transfer方法的区别是tryTransfer方法无论消费者是否接收，方法立即返回，而transfer方法是必须等到消费者消费了才返回。

## LinkedBlockingDeque
LinkedBlockingDeque是一个由链表结构组成的双向阻塞队列。
所谓双向队列指的是可以从队列的两端插入和移出元素。
双向队列因为多了一个操作队列的入口，在多线程同时入队时，也就减少了一半的竞争。
多了addFirst、addLast、offerFirst、offerLast、peekFirst和peekLast等方法。
以First单词结尾的方法，表示插入、获取（peek）或移除双端队列的第一个元素。
以Last单词结尾的方法，表示插入、获取或移除双端队列的最后一个元素。
另外，插入方法add等同于addLast，移除方法remove等效于removeFirst。
但是take方法却等同于takeFirst，不知道是不是JDK的bug，使用时还是用带有First和Last后缀的方法更清楚。
在初始化LinkedBlockingDeque时可以设置容量防止其过度膨胀。另外，双向阻塞队列可以运用在“工作窃取”模式中。
