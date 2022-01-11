package ru.playtox.kazak.accountmoneytask;

import ru.playtox.kazak.accountmoneytask.exceptions.NegativeMoney;

public class Account {
    private String id;
    private int money;

    public Account(String id, int money) {
        this.id = id;
        this.money = money;
    }

    public String getId() {
        return id;
    }

    public int getMoney() {
        return money;
    }

    public void increaseMoney(int incSum) throws NegativeMoney {
        if (incSum < 0) {
            throw new NegativeMoney();
        }
        money += incSum;
    }

    public void decreaseMoney(int decSum) throws NegativeMoney {
        if (decSum < 0 || money < decSum) {
            throw new NegativeMoney();
        }
        money -= decSum;
    }

    @Override
    public String toString() {
        return id + " : " + money;
    }
}
