package com.yuanxin.clan.core.zxing;

import java.util.ArrayList;
import java.util.List;

public class QuicklyBackNotice implements Subject{
    private static Subject subject;
    private List<Observer> observers = new ArrayList<>();

    private QuicklyBackNotice() {
        // TODO Auto-generated constructor stub
    }

    public static Subject getInstance() {
        if (null == subject) {
            synchronized (QuicklyBackNotice.class) {
                if (null == subject)
                    subject = new QuicklyBackNotice();
            }
        }
        return subject;
    }

    @Override
    public void addObserver(Observer observer) {
        // TODO Auto-generated method stub
        observers.add(observer);
    }

    @Override
    public boolean removeObserver(Observer observer) {
        // TODO Auto-generated method stub
        return observers.remove(observer);
    }

    @Override
    public void notifyObserver(int id) {
        // TODO Auto-generated method stub
        for (Observer observer : observers) {
            observer.update(id);
        }
    }
}
