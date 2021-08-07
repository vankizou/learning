package designpattern.结构型模式.桥接模式;

/**
 * 扩展抽象化角色：钱包
 *
 * @author: ZOUFANQI
 * @create: 2021-08-07 09:33
 **/
public class BagWallet extends Bag {
    @Override
    public String getName() {
        return color.getColor() + "钱包";
    }
}
