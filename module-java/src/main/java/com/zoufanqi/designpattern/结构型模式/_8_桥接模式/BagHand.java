package com.zoufanqi.designpattern.结构型模式._8_桥接模式;

/**
 * 扩展抽象化角色：手提包
 *
 * @author: ZOUFANQI
 * @create: 2021-08-07 09:33
 **/
public class BagHand extends Bag {
    @Override
    public String getName() {
        return color.getColor() + "手提包";
    }
}
