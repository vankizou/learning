package designpattern.行为型模式._23_解释器模式;

/**
 * 环境类
 *
 * @author: ZOUFANQI
 * @create: 2021-08-08 21:35
 **/
public class Context {
    private String[] cities = {"北京", "上海", "深圳"};
    private String[] persons = {"老人", "妇女", "儿童"};

    private Expression cityPerson;

    public Context() {
        Expression city = new ExpressionTerminal(cities);
        Expression person = new ExpressionTerminal(persons);

        cityPerson = new ExpressionNonTerminal(city, person);
    }

    public void freeRide(String info) {
        boolean ok = cityPerson.interpret(info);
        if (ok) {
            System.out.println("您是" + info + "，您本次乘车免费！");
        } else {
            System.out.println(info + "，您不是免费人员，本次乘车扣费2元！");
        }
    }
}
