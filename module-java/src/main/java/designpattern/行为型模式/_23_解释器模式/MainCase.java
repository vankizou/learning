package designpattern.行为型模式._23_解释器模式;

/**
 * @author: ZOUFANQI
 * @create: 2021-08-08 21:38
 **/
public class MainCase {
    /*文法规则
      <expression> ::= <city>的<person>
      <city> ::= 北京|上海
      <person> ::= 老人|妇女|儿童
    */
    public static void main(String[] args) {
        Context bus = new Context();
        bus.freeRide("北京的老人");
        bus.freeRide("北京的年轻人");
        bus.freeRide("上海的儿童");
        bus.freeRide("深圳的美女");
    }
}
