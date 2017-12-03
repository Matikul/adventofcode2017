package com.kulczyk.home.advent.ex3;

import java.util.ArrayList;
import java.util.List;

public class SpiralSolver {

    private static final int INPUT = 325489;

    public static void main(String[] args) {
        System.out.println(getSteps());
    }

    private static Integer getSteps() {
        int maxSteps = (int) Math.sqrt(INPUT);
        List<Integer> sideCenterNumbers = getSideCenters(maxSteps);
        int minDifference = sideCenterNumbers.stream()
                .map(side -> Math.abs(INPUT - side))
                .min(Integer::compare).get();
        return minDifference + maxSteps / 2;
    }

    private static List<Integer> getSideCenters(int maxSteps) {
        int lastValue = (maxSteps + 1) * (maxSteps + 1);
        List<Integer> sideCenters = new ArrayList<>();
        sideCenters.add(lastValue - maxSteps / 2);
        sideCenters.add(lastValue - maxSteps / 2 - maxSteps);
        sideCenters.add(lastValue - maxSteps / 2 - 2 * maxSteps);
        sideCenters.add(lastValue - maxSteps / 2 - 3 * maxSteps);
        return sideCenters;
    }
}
