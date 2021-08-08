package designpattern.行为型模式._23_解释器模式;

import java.util.HashSet;
import java.util.Set;

/**
 * 终结符表达式类
 *
 * @author: ZOUFANQI
 * @create: 2021-08-08 21:33
 **/
public class ExpressionTerminal implements Expression {
    private Set<String> set = new HashSet<>();

    public ExpressionTerminal(String[] data) {
        for (String s : data) {
            set.add(s);
        }
    }

    @Override
    public boolean interpret(String info) {
        return set.contains(info);
    }
}
