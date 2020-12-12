package jmh;

public interface Multiplicable<T> {
    T lift();
    Multiplicable<T> multiply(Multiplicable<T> other);
}
