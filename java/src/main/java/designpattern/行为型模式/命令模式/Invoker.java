package designpattern.行为型模式.命令模式;

/**
 * 调用者
 *
 * @author: ZOUFANQI
 * @create: 2021-08-08 12:35
 **/
public class Invoker {
    private Command command;

    public Invoker(Command command) {
        this.command = command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public void call() {
        System.out.println("调用者执行命令command...");
        command.execute();
    }
}
