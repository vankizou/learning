package designpattern.创建型模式._4_建造者模式;

/**
 * 产品：包含多个组成部件的复杂对象
 *
 * @author: ZOUFANQI
 * @create: 2021-08-05 18:58
 **/
public class Product {
    private String partA;
    private String partB;
    private String partC;

    public void setPartA(String partA) {
        this.partA = partA;
        System.out.println("建造者：构建了" + partA);
    }

    public void setPartB(String partB) {
        this.partB = partB;
        System.out.println("建造者：构建了" + partB);
    }

    public void setPartC(String partC) {
        this.partC = partC;
        System.out.println("建造者：构建了" + partC);
    }

    public void show() {
        System.out.println("建造者：我是能飞上天的产品~");
    }
}
