package com.kulczyk.home.advent.ex20;

class Particle {
    private int number;
    private int x;
    private int y;
    private int z;
    private Velocity velocity;
    private Acceleration acceleration;

    Particle(int number, int x, int y, int z, Velocity velocity, Acceleration acceleration) {
        this.number = number;
        this.x = x;
        this.y = y;
        this.z = z;
        this.velocity = velocity;
        this.acceleration = acceleration;
    }

    int absDistances() {
        return Math.abs(x) + Math.abs(y) + Math.abs(z);
    }

    int absVelocities() {
        return Math.abs(velocity.xVel) + Math.abs(velocity.yVel) + Math.abs(velocity.zVel);
    }

    int absAccelerations() {
        return Math.abs(acceleration.xAcc) + Math.abs(acceleration.yAcc) + Math.abs(acceleration.zAcc);
    }

    int getNumber() {
        return number;
    }

    void updateVelocity() {
        velocity.xVel += acceleration.xAcc;
        velocity.yVel += acceleration.yAcc;
        velocity.zVel += acceleration.zAcc;
    }

    void updatePosition() {
        x += velocity.xVel;
        y += velocity.yVel;
        z += velocity.zVel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Particle particle = (Particle) o;

        if (x != particle.x) return false;
        if (y != particle.y) return false;
        return z == particle.z;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + z;
        return result;
    }
}

class Velocity {
    int xVel;
    int yVel;
    int zVel;

    Velocity(int xVel, int yVel, int zVel) {
        this.xVel = xVel;
        this.yVel = yVel;
        this.zVel = zVel;
    }
}

class Acceleration {
    int xAcc;
    int yAcc;
    int zAcc;

    Acceleration(int xAcc, int yAcc, int zAcc) {
        this.xAcc = xAcc;
        this.yAcc = yAcc;
        this.zAcc = zAcc;
    }
}