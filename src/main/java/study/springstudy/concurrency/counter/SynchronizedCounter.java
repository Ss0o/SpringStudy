package study.springstudy.concurrency.counter;

public class SynchronizedCounter {
    private int count;

    public synchronized void increment() {
        count++;
    }

    public synchronized int getCount() {
        return count;
    }
}
