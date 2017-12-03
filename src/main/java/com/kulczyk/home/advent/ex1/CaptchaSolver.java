package com.kulczyk.home.advent.ex1;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class CaptchaSolver {

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
        if(parsedDigits.get(0).equals(parsedDigits.get(parsedDigits.size() - 1))) {
            sum += parsedDigits.get(0);
        }
        for(int i = 0; i < parsedDigits.size() - 1; i++) {
            if(parsedDigits.get(i).equals(parsedDigits.get(i + 1))) {
                sum += parsedDigits.get(i);
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
