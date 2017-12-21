package com.kulczyk.home.advent.ex19;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Tubes {

    private static final String FILENAME = "src/main/resources/tubes.txt";

    private static List<String> fields;
    private static StringBuilder message = new StringBuilder();
    private static int stepCount = 0;
    private static Point currentPoint;
    private static Point previousPoint;

    public static void main(String[] args) {
        initFields();
        traverse();
        System.out.println(stepCount);
    }

    private static void initFields() {
        fields = new ArrayList<>();
        try {
            Files.lines(Paths.get(FILENAME)).forEach(fields::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void traverse() {
        currentPoint = new Point(fields.get(0).indexOf('|'), 0);
        previousPoint = new Point(currentPoint.x, currentPoint.y);
        char currentChar;
        while ((currentChar = getCharAtPoint(currentPoint)) != ' ') {
            stepCount++;
            processChar(currentChar);
            previousPoint.setCoords(currentPoint);
            currentPoint.move();
        }
    }
    
    private static char getCharAtPoint(Point point) {
        return fields.get(point.y).charAt(point.x);
    }
    
    private static void processChar(char character) {
        if (Character.isAlphabetic(character)) {
            message.append(character);
        } else if (character == '+') {
            changeDirection(currentPoint.getPossibleNextPoints());
        } else if (character != '|' && character != '-') {
            throw new RuntimeException("Unexpected situation - strange symbol!");
        }
    }
    
    private static void changeDirection(Set<Point> points) {
        Point next = points.stream()
                .filter(p -> fields.get(p.y).charAt(p.x) != ' ')
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Unexpected situation - no valid points nearby."));
        if (next.x > currentPoint.x) {
            currentPoint.setDirection(Direction.RIGHT);
        }
        if (next.x < currentPoint.x) {
            currentPoint.setDirection(Direction.LEFT);
        }
        if (next.y > currentPoint.y) {
            currentPoint.setDirection(Direction.DOWN);
        }
        if (next.y < currentPoint.y) {
            currentPoint.setDirection(Direction.UP);
        }
    }
    
    private static class Point {
        private int x;
        private int y;
        private Direction direction;

        private Point(int x, int y) {
            this.x = x;
            this.y = y;
            direction = Direction.DOWN;
        }
        
        private void move() {
            switch (direction) {
                case UP:
                    y--;
                    break;
                case DOWN:
                    y++;
                    break;
                case LEFT:
                    x--;
                    break;
                case RIGHT:
                    x++;
                    break;
            }
        }
        
        private void setCoords(Point other) {
            this.x = other.x;
            this.y = other.y;
        }
        
        private void setDirection(Direction direction) {
            this.direction = direction;
        }
        
        private Set<Point> getPossibleNextPoints() {
            int xLimit = fields.get(0).length();
            int yLimit = fields.size();
            Set<Point> points = new HashSet<>();
            int[] xValues = new int[] {x - 1, x + 1};
            int[] yValues = new int[] {y - 1, y + 1};
            for (int xValue : xValues) {
                if (xValue >= 0 && xValue < xLimit) {
                    if (xValue != previousPoint.x || y != previousPoint.y) {
                        points.add(new Point(xValue, y));
                    }
                }
            }
            for (int yValue : yValues) {
                if (yValue >= 0 && yValue < yLimit) {
                    if (yValue != previousPoint.y || x != previousPoint.x) {
                        points.add(new Point(x, yValue));
                    }
                }
            }
            return points;
        }
    }
    
    private enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }
}
