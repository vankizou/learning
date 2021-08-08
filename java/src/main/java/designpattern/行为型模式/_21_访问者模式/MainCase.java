package designpattern.行为型模式._21_访问者模式;

/**
 * @author: ZOUFANQI
 * @create: 2021-08-08 21:18
 **/
public class MainCase {
    public static void main(String[] args) {
        ObjectStructure os = new ObjectStructure();
        os.add(new ElementImplA());
        os.add(new ElementImplB());
        Visitor visitor = new VisitorImplA();
        os.accept(visitor);
        System.out.println("-------------");
        visitor = new VisitorImplB();
        os.accept(visitor);
    }
}
