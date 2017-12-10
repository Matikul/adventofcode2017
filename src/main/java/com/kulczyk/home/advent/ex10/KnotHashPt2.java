package com.kulczyk.home.advent.ex10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class KnotHashPt2 {

    private static final String FILENAME = "src/main/resources/hashinput.txt";

    private static List<Integer> numbers = new ArrayList<>(IntStream.rangeClosed(0, 255).boxed().collect(Collectors.toList()));
    private static List<Integer> lengths = new ArrayList<>();
    private static List<Integer> denseHash = new ArrayList<>(16);
    private static int currentPosition = 0;
    private static int skipSize = 0;

    public static void main(String[] args) {
        readLengths();
        hashAll();
        denseHash.stream()
                .map(Integer::toHexString)
                .forEach(System.out::print);
    }

    private static void readLengths() {
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(FILENAME));
            for (byte aByte : bytes) {
                lengths.add((int) aByte);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        lengths.addAll(Arrays.asList(17, 31, 73, 47, 23));
    }

    private static void hashAll() {
        for (int i = 0; i < 64; i++) {
            lengths.forEach(KnotHashPt2::hashSingle);
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
