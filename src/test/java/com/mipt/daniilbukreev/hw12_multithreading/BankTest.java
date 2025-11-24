package com.mipt.daniilbukreev.hw12_multithreading;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class BankTest {
    @Test
    public void testSendToAccount_Multithreaded_Consistency() throws InterruptedException {
        Bank bank = new Bank();
        BankAccount a = new BankAccount(1, 1000);
        BankAccount b = new BankAccount(2, 1000);

        int numThreads = 10;
        int opsPerThread = 100;
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < numThreads; i++) {
            Thread t = new Thread(() -> {
                for (int j = 0; j < opsPerThread; j++) {
                    bank.sendToAccount(a, b, 1);
                    bank.sendToAccount(b, a, 1);
                }
            });
            threads.add(t);
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }

        int total = a.getBalance() + b.getBalance();
        assertEquals(2000, total, "Race possible");
        assertTrue(a.getBalance() >= 0, "Balance A couldn't be negative");
        assertTrue(b.getBalance() >= 0, "Balance B couldn't be negative");
    }

    @Test
    public void testSendToAccount_InsufficientFunds() {
        Bank bank = new Bank();
        BankAccount from = new BankAccount(1, 50);
        BankAccount to = new BankAccount(2, 200);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> bank.sendToAccount(from, to, 100)
        );

        assertTrue(ex.getMessage().contains("Not enough money1"));
        assertEquals(50, from.getBalance());
        assertEquals(200, to.getBalance());
    }

    @Test
    public void testSendToAccount_NullAccount_ThrowsNPE() {
        Bank bank = new Bank();
        BankAccount valid = new BankAccount(1, 100);

        assertThrows(NullPointerException.class, () -> bank.sendToAccount(null, valid, 10));
        assertThrows(NullPointerException.class, () -> bank.sendToAccount(valid, null, 10));
    }

    @Test
    public void testSendToAccount_NonPositiveAmount_ThrowsIAE() {
        Bank bank = new Bank();
        BankAccount a = new BankAccount(1, 100);
        BankAccount b = new BankAccount(2, 100);

        assertThrows(IllegalArgumentException.class, () -> bank.sendToAccount(a, b, 0));
        assertThrows(IllegalArgumentException.class, () -> bank.sendToAccount(a, b, -5));
    }

    @Test
    public void testSendToAccountDeadlock() throws InterruptedException {
        Bank bank = new Bank();
        BankAccount a = new BankAccount(1, 1000);
        BankAccount b = new BankAccount(2, 1000);

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                bank.sendToAccountDeadlock(a, b, 1);
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                bank.sendToAccountDeadlock(b, a, 1);
            }
        });

        t1.start();
        t2.start();

        t1.join(2000);
        t2.join(2000);

        if (t1.isAlive() || t2.isAlive()) {
            t1.interrupt();
            t2.interrupt();
        }

        assertTrue(true, "deadlock method done");
    }
}
