package io.github.madmike200590.jaspena.types.term;

public abstract class Term {

    public abstract boolean isGround();

    @Override
    public abstract boolean equals(Object o);

    @Override
    public abstract int hashCode();

    @Override
    public abstract String toString();

}
