package com.kulczyk.home.advent.ex20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Particles {

    private static final String FILENAME = "src/main/resources/particles.txt";
    private static final Pattern PATTERN =
            Pattern.compile("^p=<([-0-9]+),([-0-9]+),([-0-9]+)>, v=<([-0-9]+),([-0-9]+),([-0-9]+)>, a=<([-0-9]+),([-0-9]+),([-0-9]+)>$");

    private static List<Particle> particles;

    public static void main(String[] args) {
        initializeParticles();
        particles.sort(new ParticleComp());
        System.out.println(particles.get(0).getNumber());
    }

    private static void initializeParticles() {
        particles = new LinkedList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILENAME));
            int i = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                parseLine(i, line);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void parseLine(int particleNumber, String line) {
        Matcher matcher = PATTERN.matcher(line);
        matcher.matches();
        Velocity velocity = new Velocity(Integer.parseInt(matcher.group(4)),
                Integer.parseInt(matcher.group(5)),
                Integer.parseInt(matcher.group(6)));
        Acceleration acceleration = new Acceleration(Integer.parseInt(matcher.group(7)),
                Integer.parseInt(matcher.group(8)),
                Integer.parseInt(matcher.group(9)));
        Particle particle = new Particle(particleNumber,
                Integer.parseInt(matcher.group(1)),
                Integer.parseInt(matcher.group(2)),
                Integer.parseInt(matcher.group(3)),
                velocity,
                acceleration);
        particles.add(particleNumber, particle);
    }

    private static class ParticleComp implements Comparator<Particle> {

        @Override
        public int compare(Particle p1, Particle p2) {
            int acc1 = p1.absAccelerations();
            int acc2 = p2.absAccelerations();
            if (acc1 == acc2) {
                int vel1 = p1.absVelocities();
                int vel2 = p2.absVelocities();
                if (vel1 == vel2) {
                    return p1.absDistances() - p2.absDistances();
                }
                return vel1 > vel2 ? 1 : -1;
            }
            return acc1 > acc2 ? 1 : -1;
        }
    }
}
