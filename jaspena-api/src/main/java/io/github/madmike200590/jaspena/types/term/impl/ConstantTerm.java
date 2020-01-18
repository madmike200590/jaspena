package io.github.madmike200590.jaspena.types.term.impl;

import io.github.madmike200590.jaspena.types.term.Term;

public class ConstantTerm<T> extends Term {

    private final T value;

    public ConstantTerm(T value) {
        this.value = value;
    }

    @Override
    public boolean isGround() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ConstantTerm)) {
            return false;
        }
        ConstantTerm<?> other = (ConstantTerm<?>) o;
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

    public T getValue() {
        return this.value;
    }

}
