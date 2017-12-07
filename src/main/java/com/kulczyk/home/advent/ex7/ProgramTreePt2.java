package com.kulczyk.home.advent.ex7;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ProgramTreePt2 {

    private static final List<Leaf> leaves = new LinkedList<>();

    private static final String FILENAME = "src/main/resources/tree.txt";

    public static void main(String[] args) {
        readLeavesFromFile();
        populateConnections();
        populateBalances(findRoot());
        Leaf faulty = findFaultyLeaf();
        System.out.println(faulty);
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
        leaf.weight = Integer.valueOf(trimBrackets(tokens.get(1)));
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

    private static String trimBrackets(String number) {
        return number.substring(1, number.length() - 1);
    }

    private static void populateConnections() {
        leaves.forEach(ProgramTreePt2::populateSingleLeaf);
    }

    private static void populateSingleLeaf(Leaf leaf) {
        leaf.parentLeaf = findParent(leaf);
        leaf.childrenLeaves = findChildren(leaf);
    }

    private static Leaf findRoot() {
        return leaves.stream()
                .filter(l -> l.parentLeaf == null)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Error - no parent found."));
    }

    private static Leaf findParent(Leaf leaf) {
        return leaves.stream()
                .filter(l -> l.children.contains(leaf.name))
                .findAny()
                .orElse(null);
    }

    private static List<Leaf> findChildren(Leaf leaf) {
        return leaf.children.stream()
                .map(ProgramTreePt2::findLeafWithName)
                .collect(Collectors.toList());
    }

    private static Leaf findLeafWithName(String name) {
        return leaves.stream()
                .filter(leaf -> leaf.name.equals(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    private static void populateBalances(Leaf root) {
        if (root.childrenLeaves.isEmpty()) {
            root.balance = root.weight;
        } else {
            root.childrenLeaves.forEach(ProgramTreePt2::populateBalances);
            root.balance = sumChildrenBalances(root.childrenLeaves) + root.weight;
        }
    }

    private static int sumChildrenBalances(List<Leaf> children) {
        return children.stream()
                .mapToInt(l -> l.balance)
                .sum();
    }

    private static Leaf findFaultyLeaf() {
        return leaves.stream()
                .filter(ProgramTreePt2::inequalBalances)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Error - no faults found"));
    }

    private static boolean inequalBalances(Leaf leaf) {
        return !leaf.childrenLeaves.isEmpty()
                && !leaf.childrenLeaves.stream().map(l -> l.balance).allMatch(leaf.childrenLeaves.get(0).balance::equals);
    }

    private static class Leaf {
        private String name;
        private Integer weight;
        private List<String> children;
        private List<Leaf> childrenLeaves;
        private Leaf parentLeaf;
        private Integer balance = -1;
    }
}
