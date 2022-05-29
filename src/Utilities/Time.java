package Utilities;

public class Time {

    final int START = 0, END = 1, ELAPSED_TIME = 2;
    long[] time = new long[3];

    public Time() {
        start();
    }

    //Lapping the timer
    public long lap() {
        time[END] = System.nanoTime();
        time[ELAPSED_TIME] = time[END] - time[START];
        return time[ELAPSED_TIME];
    }

    //Get the stored time
    public float getTime(boolean seconds) {
        if (seconds)
            return inSeconds(time[ELAPSED_TIME]);
        return time[ELAPSED_TIME];
    }

    //Record the time, kind of like stopping
    public void record() {
        time[END] = System.nanoTime();
        time[ELAPSED_TIME] = time[END] - time[START];
        time[START] = System.nanoTime();
    }

    //Starts the timer
    public void start() {
        time[START] = System.nanoTime();
    }

    //Nanoseconds to seconds
    public static float inSeconds(long nanoseconds) {
        return  (float) (nanoseconds / 1e9);
    }
}
