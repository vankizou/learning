package designpattern.行为型模式._23_解释器模式;

/**
 * 非终结符表达式类
 *
 * @author: ZOUFANQI
 * @create: 2021-08-08 21:34
 **/
public class ExpressionNonTerminal implements Expression {
    private Expression city;
    private Expression person;

    public ExpressionNonTerminal(Expression city, Expression person) {
        this.city = city;
        this.person = person;
    }

    @Override
    public boolean interpret(String info) {
        String s[] = info.split("的");
        return city.interpret(s[0]) && person.interpret(s[1]);
    }
}
