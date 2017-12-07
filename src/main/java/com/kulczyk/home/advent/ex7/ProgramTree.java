package com.kulczyk.home.advent.ex7;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ProgramTree {

    private static final List<Leaf> leaves = new LinkedList<>();

    private static final String FILENAME = "src/main/resources/tree.txt";

    public static void main(String[] args) {
        readLeavesFromFile();
        System.out.println(findRoot().name);
    }

    private static void readLeavesFromFile() {
        File txt = new File(FILENAME);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(txt));
            String line;
            while ((line = reader.readLine()) != null) {
                readSingleLeaf(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readSingleLeaf(String line) {
        List<String> tokens = Arrays.asList(line.split(" "));
        Leaf leaf = new Leaf();
        leaf.name = tokens.get(0);
        List<String> trailingTokens = tokens.subList(2, tokens.size());
        if (!trailingTokens.isEmpty()) {
            trailingTokens = trailingTokens.subList(1, trailingTokens.size());
            leaf.children = trailingTokens.stream()
                    .map(name -> name.replaceAll(",", ""))
                    .collect(Collectors.toList());
        } else {
            leaf.children = new LinkedList<>();
        }
        leaves.add(leaf);
    }

    private static Leaf findRoot() {
        Leaf currentLeaf = leaves.get(0);
        Leaf parentLeaf = findParent(currentLeaf);
        while (currentLeaf != parentLeaf) {
            currentLeaf = parentLeaf;
            parentLeaf = findParent(currentLeaf);
        }
        return currentLeaf;
    }

    private static Leaf findParent(Leaf leaf) {
        return leaves.stream()
                .filter(l -> l.children.contains(leaf.name))
                .findAny()
                .orElse(leaf);
    }

    private static class Leaf {
        private String name;
        private List<String> children;
    }
}
