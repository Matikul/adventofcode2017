package com.kulczyk.home.advent.ex17;

import java.util.ArrayList;
import java.util.List;

public class Spinlock {

    private static final int STEPS = 380;
    private static final int INSERTIONS = 2017;

    private static List<Integer> numbers;

    public static void main(String[] args) {
        generateList();
        System.out.println(numbers.get(numbers.indexOf(2017) + 1));
    }

    private static void generateList() {
        numbers = new ArrayList<>(2018);
        numbers.add(0);
        int position = 1;
        for (int i = 1; i <= INSERTIONS; i++) {
            position = getNextPosition(position);
            numbers.add(position, i);
            position++;
        }
    }

    private static int getNextPosition(int currentPosition) {
        int currentSize = numbers.size();
        int shift = STEPS % currentSize;
        int nextPosition = currentPosition + shift;
        if (nextPosition > currentSize) {
            nextPosition -= currentSize;
        }
        return nextPosition;
    }
}
