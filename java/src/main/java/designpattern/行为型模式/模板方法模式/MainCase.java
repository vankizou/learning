package designpattern.行为型模式.模板方法模式;

/**
 * @author: ZOUFANQI
 * @create: 2021-08-08 11:29
 **/
public class MainCase {
    public static void main(String[] args) {
        AbstractClass cls = new AbstractClassImpl();
        cls.templateMethod();
    }
}
