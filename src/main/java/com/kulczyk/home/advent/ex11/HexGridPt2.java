package com.kulczyk.home.advent.ex11;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class HexGridPt2 {

    private static final String FILENAME = "src/main/resources/hex.txt";

    private static int x = 0;
    private static int y = 0;

    private static int maxEver = 0;

    public static void main(String[] args) {
        parseDirections();
        System.out.println(maxEver);
    }

    private static void parseDirections() {
        File txt = new File(FILENAME);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(txt));
            Arrays.stream(reader.readLine().split(","))
                    .forEach(HexGridPt2::processStep);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processStep(String step) {
        switch (step) {
            case "n":
                y++;
                break;
            case "s":
                y--;
                break;
            case "ne":
                x++;
                break;
            case "sw":
                x--;
                break;
            case "nw":
                x--;
                y++;
                break;
            case "se":
                x++;
                y--;
                break;
        }
        if (maxEver < calculateMinSteps()) {
            maxEver = calculateMinSteps();
        }
    }

    private static int calculateMinSteps() {
        return x * y < 0 ? Math.max(Math.abs(x), Math.abs(y)) : Math.abs(x) + Math.abs(y);
    }
}
