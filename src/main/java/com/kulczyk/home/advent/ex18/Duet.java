package com.kulczyk.home.advent.ex18;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Duet {

    private static final String FILENAME = "src/main/resources/assembly.txt";

    private static List<Instruction> instructions;
    private static Map<String, Integer> registers = new HashMap<>();
    private static int currentFrequency = 0;
    private static int currentPosition = 0;

    public static void main(String[] args) {
        readInstructions();

    }

    private static void readInstructions() {
        instructions = new LinkedList<>();
        try {
            Files.lines(Paths.get(FILENAME))
                    .forEach(line -> instructions.add(new Instruction(line)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void runInstructions() {
        instructions.stream().forEach(Duet::processInstruction);
    }

    private static void processInstruction(Instruction instruction) {
        switch (instruction.name) {
            case "snd":
                int freq = Integer.parseInt(instruction.arg1);

            case "set":
                break;
            case "add":
                break;
            case "mul":
                break;
            case "mod":
                break;
            case "rcv":
                break;
            case "jgz":
                break;
        }
    }

    private static void moveInstructionPointer(int shift) {
        int instructionsCount = instructions.size();
        shift %= instructionsCount;
        currentPosition += shift;
        if (currentPosition >= instructionsCount) {
            currentPosition -= instructionsCount;
        }
        if (currentPosition < 0) {
            currentPosition += instructionsCount;
        }
    }

    private static class Instruction {
        private String name = null;
        private String arg1 = null;
        private String arg2 = null;

        private Instruction(String line) {
            List<String> tokens = Arrays.asList(line.split(" "));
            this.name = tokens.get(0);
            this.arg1 = tokens.get(1);
            if (tokens.size() > 2) {
                this.arg2 = tokens.get(2);
            }
        }
    }
}
