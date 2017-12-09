package com.kulczyk.home.advent.ex9;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class StreamParserPt2 {

    private static final String FILENAME = "src/main/resources/stream.txt";

    private static int totalScore = 0;
    private static int currentScore = 0;
    private static int trashCount = 0;
    private static boolean isTrash = false;

    public static void main(String[] args) {
        parse(getLine());
        System.out.println(trashCount);
    }

    private static void parse(String line) {
        int position = 0;
        while (position < line.length()) {
            char currentSymbol = line.charAt(position);
            position = parseSymbol(currentSymbol, position);
        }
    }

    private static int parseSymbol(char currentSymbol, int position) {
        switch (currentSymbol) {
            case '!':
                return position + 2;
            case '<':
                if(isTrash) {
                    trashCount++;
                } else {
                    isTrash = true;
                }
                break;
            case '>':
                isTrash = false;
                break;
            case '{':
                if (!isTrash) {
                    currentScore++;
                }
                break;
            case '}':
                if (!isTrash) {
                    totalScore += currentScore;
                    currentScore--;
                }
                break;
            default:
                break;
        }
        if (isTrash && currentSymbol != '<') {
            trashCount++;
        }
        return position + 1;
    }

    private static String getLine() {
        File txt = new File(FILENAME);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(txt));
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
