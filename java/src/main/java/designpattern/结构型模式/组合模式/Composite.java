package designpattern.结构型模式.组合模式;

import java.util.ArrayList;
import java.util.List;

/**
 * 树枝构件
 *
 * @author: ZOUFANQI
 * @create: 2021-08-07 16:27
 **/
public class Composite implements Component {
    private List<Component> children = new ArrayList<>();

    @Override
    public void add(Component c) {
        children.add(c);
    }

    @Override
    public void remove(Component c) {
        children.remove(c);
    }

    @Override
    public Component getChild(int i) {
        return children.get(i);
    }

    @Override
    public void operation() {
        for (Component c : children) {
            c.operation();
        }
    }
}
