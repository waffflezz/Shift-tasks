package ru.shift;

public class Main {
    public static void main(String[] args) {
        MultiplicationTableGenerator table = new MultiplicationTableGenerator(0);

        System.out.println(table.getMultiplicationTable());
    }
}