package designpattern.结构型模式.桥接模式;

/**
 * 具体实现化角色
 *
 * @author: ZOUFANQI
 * @create: 2021-08-07 09:28
 **/
public class ColorRed implements Color {
    @Override
    public String getColor() {
        return "红色";
    }
}
