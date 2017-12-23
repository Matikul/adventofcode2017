package com.kulczyk.home.advent.ex20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ParticlesPt2 {

    private static final String FILENAME = "src/main/resources/particles.txt";
    private static final Pattern PATTERN =
            Pattern.compile("^p=<([-0-9]+),([-0-9]+),([-0-9]+)>, v=<([-0-9]+),([-0-9]+),([-0-9]+)>, a=<([-0-9]+),([-0-9]+),([-0-9]+)>$");

    private static List<Particle> particles;

    public static void main(String[] args) {
        initializeParticles();
        moveParticles();
        System.out.println(particles.size());
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

    private static void moveParticles() {
        for (int i = 0; i < 100000; i++) {
            particles.forEach(Particle::updateVelocity);
            particles.forEach(Particle::updatePosition);
            for (int j = 0; j < particles.size(); j++) {
                List<Particle> collided = findCollided(particles.get(j));
                if (collided.size() > 1) {
                    if (j == 0) j--;
                    particles.removeAll(collided);
                }
            }
        }
    }

    private static List<Particle> findCollided(Particle particle) {
        List<Particle> collided = new LinkedList<>();
        collided.add(particle);
        collided.addAll(particles.stream()
                .filter(p -> p.equals(particle))
                .filter(p -> !(p == particle))
                .collect(Collectors.toList()));
        return collided;
    }
}
