package com.kulczyk.home.advent.ex5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JumperPt2 {

    private static final String FILENAME = "src/main/resources/jumps.txt";

    private static int stepCount = 0;

    public static void main(String[] args) {
        jump(readJumps());
    }

    private static List<Integer> readJumps() {
        try {
            return Files.lines(Paths.get(FILENAME))
                    .mapToInt(Integer::valueOf)
                    .boxed()
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private static void jump(List<Integer> jumps) {
        int currentIndex = 0;
        int currentJump;
        try {
            while (true) {
                currentJump = jumps.get(currentIndex);
                if (currentJump >= 3) {
                    jumps.set(currentIndex, currentJump - 1);
                } else {
                    jumps.set(currentIndex, currentJump + 1);
                }
                currentIndex += currentJump;
                stepCount++;
            }
        } catch (IndexOutOfBoundsException escapeException) {
            System.out.println("Jumps: " + stepCount);
        }
    }
}
