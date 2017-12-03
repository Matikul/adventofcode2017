package com.kulczyk.home.advent.ex2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CorruptionSolverPt2 {
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
                        .sorted()
                        .collect(Collectors.toList());
                sum += findDivisor(numbersInRow);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(sum);
    }

    private static Integer findDivisor(List<Integer> numbers) {
        for (int i = numbers.size() - 1; i > 0; i--) {
            Integer number = numbers.get(i);
            Integer checked;
            for(int j = 0; (checked = numbers.get(j)) <  number / 2; j++) {
                if(number % checked == 0) {
                    return number / checked;
                }
            }
        }
        return -999999999;
    }
}
