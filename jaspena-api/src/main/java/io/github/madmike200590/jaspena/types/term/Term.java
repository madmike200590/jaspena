package io.github.madmike200590.jaspena.types.term;

public abstract class Term<T> {

    protected final T value;

    public Term(T value) {
        this.value = value;
    }

    public abstract boolean isGround();

    @Override
    public abstract boolean equals(Object o);

    @Override
    public abstract int hashCode();

    @Override
    public abstract String toString();

    public T getValue() {
        return this.value;
    }

}
