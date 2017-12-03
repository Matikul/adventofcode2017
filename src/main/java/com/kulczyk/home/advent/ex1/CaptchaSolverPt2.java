package com.kulczyk.home.advent.ex1;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class CaptchaSolverPt2 {

    private static final String FILENAME = "src/main/resources/captcha.txt";

    public static void main(String[] args) {
        String captcha = readCaptchaFromFile();
        System.out.println(processCaptcha(captcha));
    }

    private static String readCaptchaFromFile() {
        File txt = new File(FILENAME);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(txt));
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static Integer processCaptcha(String captcha) {
        List<Integer> parsedDigits = parseDigits(captcha);
        int sum = 0;
        int halfSize = parsedDigits.size() / 2;
        for (int i = 0; i < halfSize; i++) {
            if (parsedDigits.get(i).equals(parsedDigits.get(i + halfSize))) {
                sum += (2 * parsedDigits.get(i));
            }
        }
        return sum;
    }

    private static List<Integer> parseDigits(String captcha) {
        LinkedList<Integer> digits = new LinkedList<>();
        for (int i = 0; i < captcha.length(); i++) {
            digits.add(Integer.parseInt(String.valueOf(captcha.charAt(i))));
        }
        return digits;
    }
}
