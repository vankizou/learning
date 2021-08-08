package designpattern.行为型模式._15_命令模式;

/**
 * 具体命令
 *
 * @author: ZOUFANQI
 * @create: 2021-08-08 12:38
 **/
public class CommandImpl implements Command {
    private Receiver receiver;

    public CommandImpl() {
        this.receiver = new Receiver();
    }

    @Override
    public void execute() {
        receiver.action();
    }
}
