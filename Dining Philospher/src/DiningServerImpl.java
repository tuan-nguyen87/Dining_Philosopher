/**
 * DiningServer.java
 *
 * This class contains the methods called by the  philosophers.
 *
 */

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class DiningServerImpl implements DiningServer {

    private enum states {THINKING,HUNGRY,EATING}
    private static final ReentrantLock[] locks = new ReentrantLock[5];
    private static final Condition[] forks = new Condition[5];
    private static final states[] state = new states[5];

    public DiningServerImpl() {
        for (int i = 0; i < 5; i++) {
            locks[i] = new ReentrantLock();
            forks[i] = locks[i].newCondition();
            state[i] = states.THINKING;
        }
    }

    @Override
    public void takeForks(int philNumber) throws InterruptedException {
        state[philNumber] = states.HUNGRY;
        if (philNumber % 2 == 0) {
            locks[(philNumber + 1) % 5].lock();
            System.out.println("Philosopher " + philNumber + " grabs right fork");
        } else {
            locks[philNumber].lock();
            System.out.println("Philosopher " + philNumber + " grabs left fork");
        }
        //grab left fork
        System.out.println("Philosopher " + philNumber + " tries to get left fork");
        if (state[(philNumber + 4) % 5] != states.EATING && state[philNumber] == states.HUNGRY &&
                state[(philNumber + 1) % 5] == states.EATING) {
            System.out.println("waiting...");
            if (philNumber % 2 == 0) {
                forks[(philNumber + 1) % 5].await();
            } else {
                forks[philNumber].await();
            }
        }

        if (philNumber % 2 == 0) {
            locks[philNumber].lock();
            System.out.println("Philosopher " + philNumber + " grabs left fork");
        } else {
            locks[(philNumber + 1) % 5].lock();
            System.out.println("Philosopher " + philNumber + " grabs right fork");
        }
        //grab right fork
        System.out.println("Philosopher " + philNumber + " tries to get right fork");
        state[philNumber] = states.EATING;
    }

    @Override
    public void returnForks(int philNumber) {
        try {
            state[philNumber] = states.THINKING;
            if (locks[philNumber].getHoldCount() > 0) {
                //release left fork, if another thread is requesting it
                forks[philNumber].signal();
            }
            if (locks[(philNumber + 1) % 5].getHoldCount() > 0) {
                //release right fork, if another thread is requesting it
                forks[(philNumber + 1) % 5].signal();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            locks[philNumber].unlock();
            locks[(philNumber + 1) % 5].unlock();
        }
    }

    public String printStates() {
        String str = "{ ";
        for (int i = 0; i < 5; i++) {
            if (state[i] == states.THINKING) {
                str += "THINKING, ";
            } else if (state[i] == states.EATING) {
                str += "EATING, ";
            } else {
                str += "HUNGRY, ";
            }
        }
        str += "}";
        return str;
    }

}