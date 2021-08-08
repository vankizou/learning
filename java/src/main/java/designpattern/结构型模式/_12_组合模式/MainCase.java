package designpattern.结构型模式._12_组合模式;

/**
 * @author: ZOUFANQI
 * @create: 2021-08-07 16:43
 **/
public class MainCase {
    public static void main(String[] args) {
        Composite c1 = new Composite();
        Composite c2 = new Composite();

        ComponentLeaf leaf1 = new ComponentLeaf("1");
        ComponentLeaf leaf2 = new ComponentLeaf("2");
        ComponentLeaf leaf3 = new ComponentLeaf("3");

        c1.add(leaf1);
        c1.add(c2);

        c2.add(leaf2);
        c2.add(leaf3);

        c1.operation();
    }
}
