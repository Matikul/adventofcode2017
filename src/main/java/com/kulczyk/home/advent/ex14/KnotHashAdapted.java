package com.kulczyk.home.advent.ex14;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class KnotHashAdapted {

    private static List<Integer> numbers = new ArrayList<>();
    private static List<Integer> lengths = new ArrayList<>(256);
    private static List<Integer> denseHash = new ArrayList<>(16);
    private static int currentPosition = 0;
    private static int skipSize = 0;

    public static void main(String[] args) {
    }

    public static String calculate(String seed) {
        numbers = IntStream.rangeClosed(0, 255).boxed().collect(Collectors.toList());
        readLengths(seed);
        hashAll();
        numbers.clear();
        currentPosition = 0;
        skipSize = 0;
        return denseHash.stream()
                .map(num -> Integer.toHexString(0x100 | num).substring(1))
                .collect(Collectors.joining());
    }

    private static void readLengths(String seed) {
        lengths.clear();
        byte[] bytes = seed.getBytes();
        for (byte aByte : bytes) {
            lengths.add((int) aByte);
        }
        lengths.addAll(Arrays.asList(17, 31, 73, 47, 23));
    }

    private static void hashAll() {
        denseHash.clear();
        for (int i = 0; i < 64; i++) {
            lengths.forEach(KnotHashAdapted::hashSingle);
        }
        for (int i = 0; i < 16; i++) {
            int hash = 0;
            for (int j = 0; j < 16; j++) {
                hash ^= numbers.get(16 * i + j);
            }
            denseHash.add(hash);
        }
    }

    private static void hashSingle(int size) {
        if (currentPosition + size <= numbers.size()) {
            Collections.reverse(numbers.subList(currentPosition, currentPosition + size));
        } else {
            int endSize = numbers.size() - currentPosition;
            int startSize = size - endSize;
            List<Integer> part = new ArrayList<>(numbers.subList(currentPosition, currentPosition + endSize));
            part.addAll(numbers.subList(0, startSize));
            Collections.reverse(part);
            numbers.subList(currentPosition, currentPosition + endSize).clear();
            numbers.addAll(part.subList(0, endSize));
            numbers.subList(0, startSize).clear();
            numbers.addAll(0, part.subList(endSize, part.size()));
        }
        currentPosition += (size + skipSize);
        while (currentPosition >= numbers.size()) {
            currentPosition -= numbers.size();
        }
        skipSize++;
    }
}
