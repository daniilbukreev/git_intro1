package com.mipt.daniilbukreev;

public class MainClass {
    private int number;
    private String s;
    protected static double doublenumber;
    public final long longnumber = 100;

    public static void main(String[] args) {
        for (int i = 0; i < 16; i++) {
            System.out.println("Iter: " + i);
        }
    }
}
