package designpattern.行为型模式._21_访问者模式;

/**
 * 具体元素A
 *
 * @author: ZOUFANQI
 * @create: 2021-08-08 21:12
 **/
public class ElementImplB implements Element {
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public String operationB() {
        return "具体元素B的操作";
    }
}
