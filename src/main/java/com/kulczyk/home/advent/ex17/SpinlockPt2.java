package com.kulczyk.home.advent.ex17;

public class SpinlockPt2 {

    private static final int STEPS = 380;
    private static final int INSERTIONS = 500000000;

    private static int afterZero = 0;

    public static void main(String[] args) {
        generate();
        System.out.println(afterZero);
    }

    private static void generate() {
        int position = 1;
        for (int i = 1; i <= INSERTIONS; i++) {
            position = getNextPosition(i, position);
            if (position == 1) {
                afterZero = i;
            }
            position++;
        }
    }

    private static int getNextPosition(int currentSize, int currentPosition) {
        int shift = STEPS % currentSize;
        int nextPosition = currentPosition + shift;
        if (nextPosition > currentSize) {
            nextPosition -= currentSize;
        }
        return nextPosition;
    }
}
