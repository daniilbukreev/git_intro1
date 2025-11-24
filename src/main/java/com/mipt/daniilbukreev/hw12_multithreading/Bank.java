package com.mipt.daniilbukreev.hw12_multithreading;

public class Bank {
    public void sendToAccountDeadlock(BankAccount from, BankAccount to, int money) {
        validateTransfer(from, to, money);
        synchronized (from) {
            synchronized (to) {
                if (from.getBalance() < money) {
                    throw new IllegalArgumentException("Not enougn money" + from.getId());
                }
                from.withdraw(money);
                to.deposit(money);
            }
        }
    }

    public void sendToAccount(BankAccount from, BankAccount to, int money) {
        validateTransfer(from, to, money);
        if (from == to) {
            return;
        }

        BankAccount firstAccount;
        BankAccount secondAccount;

        if (from.getId() < to.getId()) {
            firstAccount = from;
            secondAccount = to;
        } else {
            firstAccount = to;
            secondAccount = from;
        }

        synchronized (firstAccount) {
            synchronized (secondAccount) {
                if (from.getBalance() < money) {
                    throw new IllegalArgumentException("Not enough money" + from.getId());
                }
                from.withdraw(money);
                to.deposit(money);
            }
        }
    }
    private void validateTransfer(BankAccount from, BankAccount to, int money) {
        if (from == null) {
            throw new NullPointerException("from cannot be null");
        }
        if (to == null) {
            throw new NullPointerException("to cannot be null");
        }
        if (money <= 0) {
            throw new IllegalArgumentException("money cannot be negative");
        }
    }
}
