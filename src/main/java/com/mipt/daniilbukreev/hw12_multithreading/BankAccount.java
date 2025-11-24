package com.mipt.daniilbukreev.hw12_multithreading;

public class BankAccount {
    private final int id;
    private int balance;

    public BankAccount(int id, int initialBalance) {
        this.id = id;
        this.balance = initialBalance;
    }

    public int getId() {
        return id;
    }

    public int getBalance() {
        return balance;
    }

    void withdraw(int money) {
        balance -= money;
    }

    void deposit(int money) {
        balance += money;
    }
}
