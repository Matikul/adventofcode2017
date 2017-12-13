package com.kulczyk.home.advent.ex12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class DigitalPlumber {

    private static final String FILENAME = "src/main/resources/pipes.txt";

    private static Map<Integer, List<Integer>> connections;
    private static Set<Integer> allChildren = new HashSet<>();

    public static void main(String[] args) {
        parseConnections();
        checkConnections(0);
        System.out.println(allChildren.size());
    }

    private static void parseConnections() {
        connections = new HashMap<>();
        try {
            Files.lines(Paths.get(FILENAME))
                    .forEach(DigitalPlumber::parseLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void parseLine(String line) {
        List<String> tokens = Arrays.asList(line.split(" <-> "));
        int key = Integer.valueOf(tokens.get(0));
        List<Integer> values = Arrays.stream(tokens.get(1).split(", "))
                .mapToInt(Integer::valueOf)
                .filter(number -> number != key)
                .boxed()
                .collect(Collectors.toList());
        connections.put(key, values);
    }

    private static void checkConnections(int nodeNumber) {
        if (!allChildren.contains(nodeNumber)) {
            allChildren.add(nodeNumber);
        }
        List<Integer> children = connections.get(nodeNumber);
        if (!children.isEmpty()) {
            children.stream()
                    .filter(number -> !allChildren.contains(number))
                    .forEach(DigitalPlumber::checkConnections);
        }
    }
}
