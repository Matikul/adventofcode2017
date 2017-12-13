package com.kulczyk.home.advent.ex13;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class PacketScanner {

    private static final String FILENAME = "src/main/resources/scanners.txt";

    private static List<Scanner> scanners;

    public static void main(String[] args) {
        parseScanners();
        System.out.println(countSeverities());
    }

    private static void parseScanners() {
        scanners = new LinkedList<>();
        try {
            Files.lines(Paths.get(FILENAME))
                    .forEach(PacketScanner::parseSingleLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void parseSingleLine(String line) {
        List<Integer> tokens = Arrays.stream(line.split(": "))
                .mapToInt(Integer::valueOf)
                .boxed()
                .collect(Collectors.toList());
        scanners.add(new Scanner(tokens.get(0), tokens.get(1)));
    }

    private static int countSeverities() {
        return scanners.stream()
                .map(scanner -> scanner.getSeverityAtTime(scanner.depth))
                .mapToInt(Integer::valueOf)
                .sum();
    }

    private static class Scanner {
        private int depth;
        private int range;

        private Scanner(int depth, int range) {
            this.depth = depth;
            this.range = range;
        }

        private int getSeverityAtTime(int time) {
            int currentPosition = getPositionAtTime(time);
            if (currentPosition != 0) {
                return 0;
            }
            return depth * range;
        }

        private int getPositionAtTime(int time) {
            int movesToReturn = range * 2 - 2;
            int reducedPosition = time;
            while (reducedPosition >= movesToReturn) {
                reducedPosition -= movesToReturn;
            }
            if (reducedPosition < range) {
                return reducedPosition;
            }
            reducedPosition -= range - 1;
            return range - 1 - reducedPosition;
        }
    }
}
