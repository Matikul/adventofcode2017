package com.kulczyk.home.advent.ex18;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Duet {

    private static final String FILENAME = "src/main/resources/assembly.txt";

    private static List<Instruction> instructions;
    private static Map<String, Long> registers = new HashMap<>();
    private static long currentFrequency = 0;
    private static int currentPosition = 0;

    public static void main(String[] args) {
        readInstructions();
        runInstructions();
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
        while (currentPosition >= 0 && currentPosition < instructions.size()) {
            processInstruction(instructions.get(currentPosition));
        }
    }

    private static void processInstruction(Instruction instruction) {
        int instructionShift = 1;
        switch (instruction.name) {
            case "snd":
                currentFrequency = resolveArgument(instruction.arg1);
                break;
            case "set":
                registers.put(instruction.arg1, resolveArgument(instruction.arg2));
                break;
            case "add":
                long toSum = resolveArgument(instruction.arg1);
                registers.put(instruction.arg1, toSum + resolveArgument(instruction.arg2));
                break;
            case "mul":
                long toMultiply = resolveArgument(instruction.arg1);
                registers.put(instruction.arg1, toMultiply * resolveArgument(instruction.arg2));
                break;
            case "mod":
                long toModulo = resolveArgument(instruction.arg1);
                registers.put(instruction.arg1, toModulo % resolveArgument(instruction.arg2));
                break;
            case "rcv":
                long regValue = resolveArgument(instruction.arg1);
                if (regValue != 0) {
                    System.out.println("Recovered frequency: " + currentFrequency);
                }
                break;
            case "jgz":
                long value = resolveArgument(instruction.arg1);
                if (value > 0) {
                    instructionShift = (int) resolveArgument(instruction.arg2);
                }
                break;
        }
        currentPosition += instructionShift;
    }

    private static long resolveArgument(String arg) {
        if (isNumber(arg)) {
            return Long.parseLong(arg);
        }
        if (registers.containsKey(arg)) {
            return registers.get(arg);
        }
        registers.put(arg, 0L);
        return 0;
    }

    private static boolean isNumber(String arg) {
        return arg.chars().allMatch(ch -> Character.isDigit(ch) || ch == '-');
    }

//    private static void moveInstructionPointer(int shift) {
//        int instructionsCount = instructions.size();
//        shift %= instructionsCount;
//        currentPosition += shift;
//        if (currentPosition >= instructionsCount) {
//            currentPosition -= instructionsCount;
//        }
//        if (currentPosition < 0) {
//            currentPosition += instructionsCount;
//        }
//    }

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
