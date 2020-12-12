package jmh;

public class PlainOldDoubleWrapper implements Multiplicable<PlainOldDoubleWrapper> {

    private final double v;

    public PlainOldDoubleWrapper(double v) {
        this.v = v;
    }

    @Override
    public PlainOldDoubleWrapper lift() {
        return this;
    }

    @Override
    public Multiplicable<PlainOldDoubleWrapper> multiply(Multiplicable<PlainOldDoubleWrapper> other) {
        return new PlainOldDoubleWrapper(this.v * other.lift().v);
    }
}
