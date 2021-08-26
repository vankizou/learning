package designpattern.行为型模式._21_访问者模式;

/**
 * 具体访问者B
 *
 * @author: ZOUFANQI
 * @create: 2021-08-08 21:15
 **/
public class VisitorImplB implements Visitor {
    @Override
    public void visit(ElementImplA element) {
        System.out.println("具体访问者B访问 -> " + element.operationA());
    }

    @Override
    public void visit(ElementImplB element) {
        System.out.println("具体访问者B访问 -> " + element.operationB());
    }
}
