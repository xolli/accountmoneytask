package ru.playtox.kazak.accountmoneytask.exceptions;

public class NegativeMoney extends Exception {
    public NegativeMoney() {
        super("Money cannot be negative");
    }
}
