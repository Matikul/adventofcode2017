package com.kulczyk.home.advent.ex15;

public class Generators {

    private static final long A_FACTOR = 16807;
    private static final long B_FACTOR = 48271;

    private static final long DIVISION_FACTOR = 2147483647;

    private static long A_CURRENT = 722;
    private static long B_CURRENT = 354;

    public static void main(String[] args) {
        System.out.println(countMatches());
    }

    private static int countMatches() {
        int matches = 0;
        for(int i = 0; i < 5000000; i++) {
            if (hasMatchingEnding(generateNextA(), generateNextB())) {
                matches++;
            }
        }
        return matches;
    }

    private static long generateNextA() {
        A_CURRENT = (A_CURRENT * A_FACTOR) % DIVISION_FACTOR;
        while (A_CURRENT % 4 != 0) {
            A_CURRENT = (A_CURRENT * A_FACTOR) % DIVISION_FACTOR;
        }
        return A_CURRENT;
    }

    private static long generateNextB() {
        B_CURRENT = (B_CURRENT * B_FACTOR) % DIVISION_FACTOR;
        while (B_CURRENT % 8 != 0) {
            B_CURRENT = (B_CURRENT * B_FACTOR) % DIVISION_FACTOR;
        }
        return B_CURRENT;
    }

    private static boolean hasMatchingEnding(long a, long b) {
        return (a & 0xffff) == (b & 0xffff);
    }
}
