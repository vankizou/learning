package com.zoufanqi.designpattern.行为型模式._19_中介者模式;

import java.util.ArrayList;
import java.util.List;

/**
 * 具体中介者
 *
 * @author: ZOUFANQI
 * @create: 2021-08-08 20:21
 **/
public class MediatorImpl extends Mediator {
    private List<Colleague> colleagues = new ArrayList<Colleague>();

    @Override
    public void register(Colleague colleague) {
        if (!colleagues.contains(colleague)) {
            colleagues.add(colleague);
            colleague.setMediator(this);
        }
    }

    @Override
    public void relay(Colleague colleague) {
        for (Colleague c : colleagues) {
            if (!c.equals(colleague)) {
                c.receive();
            }
        }
    }
}
