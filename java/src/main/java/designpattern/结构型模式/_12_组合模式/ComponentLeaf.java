package designpattern.结构型模式._12_组合模式;

/**
 * 树叶构件
 *
 * @author: ZOUFANQI
 * @create: 2021-08-07 15:02
 **/
public class ComponentLeaf implements Component {
    private String name;

    public ComponentLeaf(String name) {
        this.name = name;
    }

    @Override
    public void add(Component c) {

    }

    @Override
    public void remove(Component c) {

    }

    @Override
    public Component getChild(int i) {
        return null;
    }

    @Override
    public void operation() {
        System.out.println("树叶【" + this.name + "】被访问");
    }
}
