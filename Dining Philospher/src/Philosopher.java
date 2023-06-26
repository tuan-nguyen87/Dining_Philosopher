/**
 * Philosopher.java
 *
 * This class represents each philosopher thread.
 * Philosophers alternate between eating and thinking.
 *
 */

import java.util.Random;

public class Philosopher implements Runnable
{
    private final int id;
    private final DiningServer ds;
    private final Random numGenerator = new Random();

    //constructor initializes philosopher number and Dining Server object
    public Philosopher(int id, DiningServer ds) {
        this.id = id;
        this.ds = ds;
    }

    @Override
    public void run() {
        int randNum = 0;
        while (true) {
            try {
                ds.takeForks(this.id); //philosopher gets both forks here
                System.out.println(this + " has both forks");
                randNum = numGenerator.nextInt(1000, 3000);
                System.out.println(this + " eating for " + randNum + " ms");
                Thread.sleep(randNum); //puts thread to sleep for certain amount of milliseconds
                ds.returnForks(this.id); //philosopher puts back both forks here
                randNum = numGenerator.nextInt(1000, 3000);
                System.out.println(this + " thinking for " + randNum + " ms");
                Thread.sleep(randNum); //puts thread to sleep for certain amount of milliseconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        return "Philosopher " + this.id;
    }
}