package io.github.madmike200590.jaspena.types.term.impl;

import io.github.madmike200590.jaspena.exception.AspSyntaxException;
import io.github.madmike200590.jaspena.types.Formats;
import io.github.madmike200590.jaspena.types.term.Term;

public class VariableTerm extends Term {

    private final String name;

    public VariableTerm(String name) {
        if (!Formats.matchesVariableFormat(name)) {
            throw new AspSyntaxException("Not a valid variable name: " + name);
        }
        this.name = name;
    }

    @Override
    public boolean isGround() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof VariableTerm)) {
            return false;
        }
        VariableTerm other = (VariableTerm) o;
        return this.name.equals(other.name);
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public String toString() {
        return this.name;
    }

}
