package com.kulczyk.home.advent.ex10;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class KnotHash {

    private static List<Integer> numbers = new ArrayList<>(IntStream.rangeClosed(0, 255).boxed().collect(Collectors.toList()));
    private static List<Integer> lengths = Arrays.asList(83,0,193,1,254,237,187,40,88,27,2,255,149,29,42,100);
    private static int currentPosition = 0;
    private static int skipSize = 0;

    public static void main(String[] args) {
        hashAll();
        System.out.println(numbers.get(0) * numbers.get(1));
    }

    private static void hashAll() {
        lengths.forEach(KnotHash::hashSingle);
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
        if (currentPosition >= numbers.size()) {
            currentPosition -= numbers.size();
        }
        skipSize++;
    }
}
