package com.kulczyk.home.advent.ex3;

import java.util.HashMap;
import java.util.Map;

public class SpiralSolverPt2 {

    private static final int INPUT = 325489;

    private static Tuple currentTuple = new Tuple(1, 0);

    public static void main(String[] args) {
        System.out.println(getFirstLarger());
    }

    private static int getFirstLarger() {
        Map<Tuple, Integer> spiral = new HashMap<>();
        spiral.put(new Tuple(0, 0), 1);
        spiral.put(new Tuple(1, 0), 1);
        Integer value;
        do {
            value = nextValue(spiral);
        }
        while (value < INPUT);
        return value;
    }

    private static Integer nextValue(Map<Tuple, Integer> spiral) {
        if(toUp(spiral)) {
            currentTuple.y += 1;
        }
        else if (toLeft(spiral)) {
            currentTuple.x -= 1;
        }
        else if (toDown(spiral)) {
            currentTuple.y -= 1;
        }
        else if (toRight(spiral)) {
            currentTuple.x += 1;
        }
        else {
            throw new RuntimeException("Unexpected state!");
        }
        int currentValue = sumNeighbors(spiral);
        spiral.put(new Tuple(currentTuple.x, currentTuple.y), currentValue);
        return currentValue;
    }

    private static boolean toLeft(Map<Tuple, Integer> spiral) {
        return spiral.containsKey(new Tuple(currentTuple.x, currentTuple.y - 1))
                && !spiral.containsKey(new Tuple(currentTuple.x - 1, currentTuple.y));
    }

    private static boolean toRight(Map<Tuple, Integer> spiral) {
        return spiral.containsKey(new Tuple(currentTuple.x, currentTuple.y + 1))
                && !spiral.containsKey(new Tuple(currentTuple.x + 1, currentTuple.y));
    }

    private static boolean toUp(Map<Tuple, Integer> spiral) {
        return spiral.containsKey(new Tuple(currentTuple.x - 1, currentTuple.y))
                && !spiral.containsKey(new Tuple(currentTuple.x, currentTuple.y + 1));
    }

    private static boolean toDown(Map<Tuple, Integer> spiral) {
        return spiral.containsKey(new Tuple(currentTuple.x + 1, currentTuple.y))
                && !spiral.containsKey(new Tuple(currentTuple.x, currentTuple.y - 1));
    }

    private static int sumNeighbors(Map<Tuple, Integer> spiral) {
        int sum = 0;
        for (int i = currentTuple.x - 1; i <= currentTuple.x + 1; i++) {
            for (int j = currentTuple.y - 1; j <= currentTuple.y + 1; j++) {
                if(spiral.containsKey(new Tuple(i, j))) {
                    sum += spiral.get(new Tuple(i, j));
                }
            }
        }
        return sum;
    }

    static class Tuple {
        Integer x;
        Integer y;

        Tuple(int px, int py) {
            x = px;
            y = py;
        }

        @Override
        public int hashCode() {
            return 31*x + y;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Tuple) {
                Tuple t = (Tuple) obj;
                return x.equals(t.x) && y.equals(t.y);
            }
            return false;
        }
    }
}
