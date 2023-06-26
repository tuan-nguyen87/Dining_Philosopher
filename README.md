# Dining_Philosopher
A solution to the Chinese Dining philosopher problem.

To run this application please have Amazon Correto 19 installed on your machine and run "DiningPhilosophers.java" file.

The Dining Philosopher is used to describe synchronization in a multi-threaded environment. Five philosphers are sitting around a dining table, with five plates and five forks. All the philospher can do is think and eat, however, they can only eat if they have two forks (left and right fork). Problem arises when a philopsher want to eat but is unable to due lack of access to forks, or resources. If each philosopher is holding onto a fork and does not share we will have a deadlock program. This application will synchronize the sharing of resources so that any of our philosopher can eat when he wants to eat.
