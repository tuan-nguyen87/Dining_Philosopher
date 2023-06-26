/**
 * DiningPhilosophers.java
 *
 * This program starts the dining philosophers problem.
 *
 */


public class DiningPhilosophers
{
   public static void main(String[] args)
   {
      DiningServer ds = new DiningServerImpl();
      Philosopher[] philosophers = new Philosopher[5];
      for (int i = 0; i < 5; i++) {
         philosophers[i] = new Philosopher(i,ds);
         new Thread(philosophers[i]).start();
      }

   }
}
