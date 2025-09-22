package com.mipt.daniilbukreev.model;

public class Human {
    private String name;
    private String surname;
    private int age;
    private boolean isEmployed;

    public void setname(String name) {
        this.name = name;
    }
    public void setsurname(String surname) {
        this.surname = surname;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setEmployed(boolean employed) {
        this.isEmployed = employed;
    }

    public String getname() {
        return name;
    }
    public String getsurname() {
        return surname;
    }
    public int getAge() {
        return age;
    }
    public boolean isEmployed() {
        return isEmployed;
    }

}
