package designpattern.行为型模式.状态模式;

/**
 * 环境类
 *
 * @author: ZOUFANQI
 * @create: 2021-08-08 13:13
 **/
public class Context {
    private State state;

    public Context() {
        this.state = new StateImplA();
    }

    /**
     * 对请求做处理
     */
    public void handle() {
        state.handle(this);
    }

    /**
     * 读取状态
     *
     * @return
     */
    public State getState() {
        return state;
    }

    /**
     * 设置新状态
     *
     * @param state
     */
    public void setState(State state) {
        this.state = state;
    }
}
