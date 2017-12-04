package com.kulczyk.home.advent.ex4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

public class PassphraseChecker {

    private static final String FILENAME = "src/main/resources/passphrases.txt";

    public static void main(String[] args) {
        System.out.println(checkPasses());
    }

    private static int checkPasses() {
        try {
            return Files.lines(Paths.get(FILENAME))
                    .map(PassphraseChecker::splitLine)
                    .mapToInt(PassphraseChecker::checkDuplicates)
                    .sum();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private static String[] splitLine(String line) {
        return line.split(" ");
    }

    private static int checkDuplicates(String[] lineWords) {
        int maxCount = Arrays.stream(lineWords)
                .mapToInt(word -> Collections.frequency(Arrays.asList(lineWords), word))
                .max()
                .orElseThrow(() -> new RuntimeException("Problem with checking duplicates"));
        return (maxCount == 1) ? 1 : 0;
    }
}
