package designpattern.结构型模式.桥接模式;

/**
 * @author: ZOUFANQI
 * @create: 2021-08-07 09:35
 **/
public class MainCase {
    public static void main(String[] args) {
        ColorGreen colorGreen = new ColorGreen();
        ColorRed colorRed = new ColorRed();

        BagHand bagHand = new BagHand();
        bagHand.setColor(colorRed);
        System.out.println(bagHand.getName());

        BagWallet bagWallet = new BagWallet();
        bagWallet.setColor(colorGreen);
        System.out.println(bagWallet.getName());
    }
}
