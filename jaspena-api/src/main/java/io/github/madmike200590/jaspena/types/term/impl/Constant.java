package io.github.madmike200590.jaspena.types.term.impl;

import io.github.madmike200590.jaspena.types.term.Term;

public class Constant<T> extends Term<T> {

    public Constant(T value) {
        super(value);
    }

    @Override
    public boolean isGround() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Constant)) {
            return false;
        }
        Constant<?> other = (Constant<?>) o;
        return this.value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }

    @Override
    public String toString() {
        return this.value.toString();
    }

}
