package designpattern.行为型模式._15_命令模式;

/**
 * @author: ZOUFANQI
 * @create: 2021-08-08 12:40
 **/
public class MainCase {
    public static void main(String[] args) {
        Command command = new CommandImpl();
        Invoker invoker = new Invoker(command);
        invoker.call();
    }
}
