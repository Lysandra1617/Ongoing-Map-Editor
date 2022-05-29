package Utilities;

public class Pair<T1, T2> {
    T1 first;
    T2 second;

    public Pair(T1 f, T2 s) {
        first = f;
        second = s;
    }

    public T1 getFirst() {
        return first;
    }

    public T2 getSecond() {
        return second;
    }

    public void setFirst(T1 first) {
        this.first = first;
    }

    public void setSecond(T2 second) {
        this.second = second;
    }
}
