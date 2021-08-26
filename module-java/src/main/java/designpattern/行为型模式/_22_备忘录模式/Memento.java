package designpattern.行为型模式._22_备忘录模式;

/**
 * 备忘录
 *
 * @author: ZOUFANQI
 * @create: 2021-08-08 21:23
 **/
public class Memento {
    private String state;

    public Memento(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
