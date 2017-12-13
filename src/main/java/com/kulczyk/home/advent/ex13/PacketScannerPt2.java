package com.kulczyk.home.advent.ex13;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class PacketScannerPt2 {

    private static final String FILENAME = "src/main/resources/scanners.txt";

    private static List<Scanner> scanners;

    public static void main(String[] args) {
        parseScanners();
        System.out.println(getMinOffset());
    }

    private static void parseScanners() {
        scanners = new LinkedList<>();
        try {
            Files.lines(Paths.get(FILENAME))
                    .forEach(PacketScannerPt2::parseSingleLine);
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

    private static long getMinOffset() {
        long offset = 0;
        while (isCaught(offset)) {
            offset++;
        }
        return offset;
    }

    private static boolean isCaught(long offset) {
        return scanners.stream()
                .anyMatch(scanner -> scanner.isZeroAtTime(scanner.depth + offset));
    }

    private static class Scanner {
        private int depth;
        private int range;

        private Scanner(int depth, int range) {
            this.depth = depth;
            this.range = range;
        }

        private boolean isZeroAtTime(long time) {
            return time % (range * 2 - 2) == 0;
        }
    }
}
