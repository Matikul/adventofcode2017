package com.kulczyk.home.advent.ex4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

public class PassphraseCheckerPt2 {

    private static final String FILENAME = "src/main/resources/passphrases.txt";

    public static void main(String[] args) {
        System.out.println(checkPasses());
    }

    private static int checkPasses() {
        try {
            return Files.lines(Paths.get(FILENAME))
                    .map(PassphraseCheckerPt2::splitLine)
                    .map(PassphraseCheckerPt2::sortWords)
                    .mapToInt(PassphraseCheckerPt2::checkDuplicates)
                    .sum();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private static String[] splitLine(String line) {
        return line.split(" ");
    }

    private static String[] sortWords(String[] lineWords) {
        return Arrays.stream(lineWords)
                .map(PassphraseCheckerPt2::sortSingleWord)
                .toArray(String[]::new);
    }

    private static String sortSingleWord(String word) {
        return Arrays.stream(word.split(""))
                .sorted()
                .collect(Collectors.joining());

    }

    private static int checkDuplicates(String[] lineWords) {
        int maxCount = Arrays.stream(lineWords)
                .mapToInt(word -> Collections.frequency(Arrays.asList(lineWords), word))
                .max()
                .orElseThrow(() -> new RuntimeException("Problem with checking duplicates"));
        return (maxCount == 1) ? 1 : 0;
    }
}
