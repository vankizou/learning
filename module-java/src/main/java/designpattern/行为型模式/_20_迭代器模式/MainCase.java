package designpattern.行为型模式._20_迭代器模式;

/**
 * @author: ZOUFANQI
 * @create: 2021-08-08 20:38
 **/
public class MainCase {
    public static void main(String[] args) {
        Aggregate ag = new AggregateImpl();
        ag.add("北京大学");
        ag.add("清华大学");
        ag.add("复旦大学");

        System.out.println("聚合的内容有：");

        Iterator it = ag.getIterator();
        while (it.hasNext()) {
            Object o = it.next();
            System.out.print(o + " ");
        }
        System.out.println("\n--------");
        System.out.println("first: " + it.first());
    }
}
