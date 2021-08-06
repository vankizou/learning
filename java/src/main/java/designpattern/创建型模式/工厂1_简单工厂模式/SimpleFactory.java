package designpattern.创建型模式.工厂1_简单工厂模式;

/**
 * @author: ZOUFANQI
 * @create: 2021-08-04 19:03
 **/
public class SimpleFactory {

    public static void main(String[] args) {
        createProduct(ProductType.PRODUCT1).show();
        createProduct(ProductType.PRODUCT2).show();
    }

    public static Product createProduct(ProductType productType) {
        switch (productType) {
            case PRODUCT1:
                return new Product1();
            case PRODUCT2:
                return new Product2();
        }
        return null;
    }

    enum ProductType {
        PRODUCT1,
        PRODUCT2,
    }

}
