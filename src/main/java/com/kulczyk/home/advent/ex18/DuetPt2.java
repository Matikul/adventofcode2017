package com.kulczyk.home.advent.ex18;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class DuetPt2 {

    private static final String FILENAME = "src/main/resources/assembly.txt";

    private static List<Instruction> instructions;
    private Map<String, Long> registers = new HashMap<>();
    private Queue<Long> received = new LinkedList<>();
    private int currentPosition = 0;
    private DuetPt2 otherProgram = null;
    private boolean isLocked = false;
    private int sendCount = 0;

    public static void main(String[] args) {
        readInstructions();
        runPrograms();
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

    private static void runPrograms() {
        DuetPt2 program0 = new DuetPt2();
        DuetPt2 program1 = new DuetPt2();
        program0.registers.put("p", 0L);
        program1.registers.put("p", 1L);
        program0.otherProgram = program1;
        program1.otherProgram = program0;
        while (!program0.isLocked || !program1.isLocked) {
            if (!program0.isLocked) {
                program0.processInstruction(instructions.get(program0.currentPosition));
            }
            if (!program1.isLocked) {
                program1.processInstruction(instructions.get(program1.currentPosition));
            }
        }
        System.out.println(program1.sendCount);
    }

    private void processInstruction(Instruction instruction) {
        int instructionShift = 1;
        switch (instruction.name) {
            case "snd":
                send(resolveArgument(instruction.arg1));
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
                if (received.isEmpty()) {
                    isLocked = true;
                    instructionShift = 0;
                } else {
                    registers.put(instruction.arg1, received.remove());
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

    private long resolveArgument(String arg) {
        if (isNumber(arg)) {
            return Long.parseLong(arg);
        }
        if (registers.containsKey(arg)) {
            return registers.get(arg);
        }
        registers.put(arg, 0L);
        return 0;
    }

    private boolean isNumber(String arg) {
        return arg.chars().allMatch(ch -> Character.isDigit(ch) || ch == '-');
    }

    private void send(long value) {
        sendCount++;
        otherProgram.receive(value);
    }

    private void receive(long value) {
        if (isLocked) {
            isLocked = false;
        }
        received.add(value);
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
