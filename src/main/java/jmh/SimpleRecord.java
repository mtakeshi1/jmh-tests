package jmh;

public record SimpleRecord(double v0) implements Multiplicable<SimpleRecord> {

    @Override
    public SimpleRecord lift() {
        return this;
    }

    public SimpleRecord multiply(Multiplicable<SimpleRecord> other) {
        return new SimpleRecord(this.v0 * other.lift().v0);
    }
}
