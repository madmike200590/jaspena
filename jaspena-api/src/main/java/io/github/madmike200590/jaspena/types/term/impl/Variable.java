package io.github.madmike200590.jaspena.types.term.impl;

import io.github.madmike200590.jaspena.exception.AspSyntaxException;
import io.github.madmike200590.jaspena.types.Formats;
import io.github.madmike200590.jaspena.types.term.Term;

public class Variable extends Term<String> {

    public Variable(String value) {
        super(value);
        if (!Formats.matchesVariableFormat(value)) {
            throw new AspSyntaxException("Not a valid variable name: " + value);
        }
    }

    @Override
    public boolean isGround() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Variable)) {
            return false;
        }
        Variable other = (Variable) o;
        return this.value.equals(other.value);
    }

    public int hashCode() {
        return this.value.hashCode();
    }

    @Override
    public String toString() {
        return this.value;
    }

}
