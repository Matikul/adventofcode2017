package com.kulczyk.home.advent.ex14;

import com.sun.javafx.scene.traversal.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Defragmentation {

    private static final String INPUT = "amgozmfv";

    public static void main(String[] args) {
        System.out.println(calculateAll());
    }

    private static int calculateAll() {
        List<String> inputs = new ArrayList<>(128);
        IntStream.rangeClosed(0, 127)
                .forEach(num -> inputs.add(addTrail(INPUT, num)));
        return inputs.stream()
                .map(KnotHashAdapted::calculate)
                .mapToInt(Defragmentation::countSetBitsInHex)
                .sum();
    }

    private static String addTrail(String initial, int number) {
        return initial + "-" + number;
    }

    private static int countSetBitsInHex(String hexNumber) {
        int sum = 0;
        for (int i = 0; i < 4; i++) {
            String part = hexNumber.substring(i * 8, i * 8 + 8);
            sum += Long.bitCount(Long.parseLong(part, 16));
        }
        return sum;
    }
}
