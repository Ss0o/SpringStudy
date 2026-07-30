package study.springstudy.concurrency.counter;

public class UnsafeCounter {
    private int count;

    public void increment() {
        count++;
    }
    public int getCount() {
        return count;
    }
}
