package com.kulczyk.home.advent.ex14;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DefragmentationPt2 {

    private static final String INPUT = "amgozmfv";

    private static boolean[][] table = new boolean[128][128];

    public static void main(String[] args) {
        fillTable();
        System.out.println(countGroups());
    }

    private static void fillTable() {
        List<String> inputs = new ArrayList<>(128);
        IntStream.rangeClosed(0, 127)
                .forEach(num -> inputs.add(addTrail(INPUT, num)));
        List<String> bits = inputs.stream()
                .map(KnotHashAdapted::calculate)
                .map(DefragmentationPt2::fillForSingleLine)
                .collect(Collectors.toList());
        for (int i = 0; i < 128; i++) {
            String line = bits.get(i);
            for (int j = 0; j < 128; j++) {
                table[i][j] = line.charAt(j) == '1';
            }
        }
    }

    private static int countGroups() {
        int groupsCount = 0;
        for (int i = 0; i < 128; i++) {
            for (int j = 0; j < 128; j++) {
                if (table[i][j]) {
                    groupsCount++;
                    reduceGroup(i, j);
                }
            }
        }
        return groupsCount;
    }

    private static void reduceGroup(int x, int y) {
        int border = 127;
        table[x][y] = false;
        for (Neighbor neighbor : Neighbor.getNeighbors(x, y, border)) {
            if (table[neighbor.x][neighbor.y]) {
                reduceGroup(neighbor.x, neighbor.y);
            }
        }
    }

    private static String addTrail(String initial, int number) {
        return initial + "-" + number;
    }

    private static String fillForSingleLine(String hexNumber) {
        StringBuilder currentLine = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            String part = hexNumber.substring(i * 4, i * 4 + 4);
            currentLine.append(Long.toBinaryString(0x10000 | Long.parseLong(part, 16)).substring(1));
        }
        return currentLine.toString();
    }

    private static class Neighbor {
        private int x, y;

        private Neighbor(int px, int py) {x = px; y=py;}

        private static List<Neighbor> getNeighbors(int x, int y, int border) {
            List<Neighbor> neighbors = new ArrayList<>(4);
            int[] xAround = new int[] {x - 1, x + 1};
            int[] yAround = new int[] {y - 1, y + 1};
            for (int xx : xAround) {
                if (xx >= 0 && xx <= border) {
                    neighbors.add(new Neighbor(xx, y));
                }
            }
            for (int yy : yAround) {
                if (yy >= 0 && yy <= border) {
                    neighbors.add(new Neighbor(x, yy));
                }
            }
            return neighbors;
        }
    }
}
