package com.kulczyk.home.advent.ex2;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class CorruptionSolver {

    private static final String FILENAME = "src/main/resources/matrix.txt";

    public static void main(String[] args) {
        processMatrix();
    }

    private static void processMatrix() {
        File txt = new File(FILENAME);
        String line;
        List<Integer> numbersInRow;
        int sum = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(txt));
            while((line = reader.readLine()) != null) {
                numbersInRow = Arrays.stream(line.split("\t"))
                        .map(String::valueOf)
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
                sum += getMax(numbersInRow) - getMin(numbersInRow);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(sum);
    }

    private static Integer getMax(List<Integer> numbers) {
        return numbers.stream().max((Integer::compare)).get();
    }

    private static Integer getMin(List<Integer> numbers) {
        return numbers.stream().min((Integer::compare)).get();
    }
}
