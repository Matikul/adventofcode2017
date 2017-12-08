package com.kulczyk.home.advent.ex8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EvaluatorPt2 {

    private static final String FILENAME = "src/main/resources/instructions.txt";

    private static final Map<String, Integer> variables = new HashMap<>();

    private static int maxEver = 0;

    public static void main(String[] args) {
        evaluate();
        System.out.println(maxEver);
    }

    private static void evaluate() {
        try {
            Files.lines(Paths.get(FILENAME))
                    .forEach(EvaluatorPt2::processLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processLine(String line) {
        List<String> tokens = Arrays.asList(line.split(" "));
        if (!variables.containsKey(tokens.get(0))) {
            variables.put(tokens.get(0), 0);
        }
        if (!variables.containsKey(tokens.get(4))) {
            variables.put(tokens.get(4), 0);
        }
        if (evalCondition(tokens.get(4), tokens.get(5), Integer.valueOf(tokens.get(6)))) {
            evalExpression(tokens.get(0), tokens.get(1), Integer.valueOf(tokens.get(2)));
        }
    }

    private static void evalExpression(String variable, String operation, int value) {
        int variableValue = variables.get(variable);
        switch (operation) {
            case "inc":
                variableValue += value;
                break;
            case "dec":
                variableValue -= value;
                break;
            default:
                throw new RuntimeException("Unexpected situation - unknown operation " + operation);
        }
        variables.put(variable, variableValue);
        if (variableValue > maxEver) {
            maxEver = variableValue;
        }
    }

    private static boolean evalCondition(String variable, String operator, int value) {
        switch (operator) {
            case "==":
                return variables.get(variable) == value;
            case "!=":
                return variables.get(variable) != value;
            case ">":
                return variables.get(variable) > value;
            case ">=":
                return variables.get(variable) >= value;
            case "<":
                return variables.get(variable) < value;
            case "<=":
                return variables.get(variable) <= value;
            default:
                throw new RuntimeException("Unexpected situation - unknown operator " + operator);
        }
    }
}
