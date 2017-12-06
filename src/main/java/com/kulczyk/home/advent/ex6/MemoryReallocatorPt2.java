package com.kulczyk.home.advent.ex6;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class MemoryReallocatorPt2 {

    private static final String FILENAME = "src/main/resources/registers.txt";

    public static void main(String[] args) {
        System.out.println(processRegisters(readRegistersFromFile()));
    }

    private static List<Integer> readRegistersFromFile() {
        File txt = new File(FILENAME);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(txt));
            return Arrays.stream(reader.readLine().split("\t"))
                    .mapToInt(Integer::valueOf)
                    .boxed()
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(0);
    }

    private static int processRegisters(List<Integer> registers) {
        int iterations = 0;
        List<List<Integer>> previousStates = new LinkedList<>();
        while(!previousStates.contains(registers)) {
            previousStates.add(new ArrayList<>(registers));
            mutate(registers);
            iterations++;
        }
        return iterations - previousStates.indexOf(registers);
    }

    private static void mutate(List<Integer> registers) {
        Pair<Integer, Integer> max = getMaxIndexAndValue(registers);
        int index = max.getKey();
        int value = max.getValue();
        registers.set(index, 0);
        while(value > 0) {
            index++;
            if(index >= registers.size()) {
                index = 0;
            }
            registers.set(index, registers.get(index) + 1);
            value--;
        }
    }

    private static Pair<Integer, Integer> getMaxIndexAndValue(List<Integer> numbers) {
        int max = numbers.stream()
                .max(Integer::compare)
                .get();
        int index = numbers.indexOf(max);
        return new Pair<>(index, max);
    }
}
