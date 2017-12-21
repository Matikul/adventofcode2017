package com.kulczyk.home.advent.ex16;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ProgramDancePt2 {

    private static final String FILENAME = "src/main/resources/dancesteps.txt";

    private static final List<String> programs = new LinkedList<>(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p"));

    public static void main(String[] args) {
        System.out.println(billionCycles());
    }

    private static String billionCycles() {
        Map<Integer, String> iterations = new LinkedHashMap<>();
        List<String> steps = parseSteps();
        int i = 0;
        while (true) {
            String result = dance(steps);
            if (iterations.containsValue(result)) {
                break;
            }
            i++;
            iterations.put(i, result);
        }
        return iterations.get(1000000000 % i);
    }

    private static List<String> parseSteps() {
        File txt = new File(FILENAME);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(txt));
            return Arrays.asList(reader.readLine().split(","));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    private static String dance(List<String> steps) {
        steps.forEach(ProgramDancePt2::parseInstruction);
        return String.join("", programs);
    }

    private static void parseInstruction(String instruction) {
        if (instruction.startsWith("s")) {
            spin(Integer.parseInt(instruction.substring(1)));
        } else if (instruction.startsWith("p")) {
            List<String> progs = Arrays.asList(instruction.substring(1).split("/"));
            String prog1 = progs.get(0);
            String prog2 = progs.get(1);
            partner(prog1, prog2);
        } else if (instruction.startsWith("x")) {
            List<String> progs = Arrays.asList(instruction.substring(1).split("/"));
            int pos1 = Integer.parseInt(progs.get(0));
            int pos2 = Integer.parseInt(progs.get(1));
            exchange(pos1, pos2);
        }
    }

    private static void spin(int count) {
        List<String> ending = new LinkedList<>(programs.subList(programs.size() - count, programs.size()));
        programs.removeAll(ending);
        programs.addAll(0, ending);
    }

    private static void partner(String prog1, String prog2) {
        int pos1 = programs.indexOf(prog1);
        int pos2 = programs.indexOf(prog2);
        exchange(pos1, pos2);
    }

    private static void exchange(int pos1, int pos2) {
        Collections.swap(programs, pos1, pos2);
    }
}
