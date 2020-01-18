package io.github.madmike200590.jaspena.core;

import io.github.madmike200590.jaspena.exception.AspMappingException;
import io.github.madmike200590.jaspena.types.Atom;
import io.github.madmike200590.jaspena.types.term.Term;
import io.github.madmike200590.jaspena.types.term.impl.ConstantTerm;

public final class AtomDataExtractors {

    private AtomDataExtractors() {

    }

    public static Boolean atomToBoolean(Atom atom) throws AspMappingException {
        if (atom.getPredicate().getArity() != 0) {
            throw new AspMappingException("Cannot map from " + atom.getPredicate().getArity() + "-ary predicate "
                    + atom.getPredicate().getSymbol() + " to boolean, expecting propositional constant!");
        }
        return Boolean.TRUE;
    }

    public static String atomToString(Atom atom) throws AspMappingException {
        if (atom.getPredicate().getArity() != 1) {
            throw new AspMappingException("Cannot map from " + atom.getPredicate().getArity() + "-ary predicate "
                    + atom.getPredicate().getSymbol() + " to String, expecting unary predicate!");
        }
        Term term = atom.getTerms().get(0);
        if (!(term instanceof ConstantTerm)) {
            throw new AspMappingException("Cannot map from term of type " + term.getClass().getSimpleName()
                    + " to String, expecting a ConstantTerm!");
        }
        return ((ConstantTerm<?>) term).getValue().toString();
    }

    public static Integer atomToInteger(Atom atom) throws AspMappingException {
        if (atom.getPredicate().getArity() != 1) {
            throw new AspMappingException("Cannot map from " + atom.getPredicate().getArity() + "-ary predicate "
                    + atom.getPredicate().getSymbol() + " to Integer, expecting unary predicate!");
        }
        Term term = atom.getTerms().get(0);
        if (!(term instanceof ConstantTerm)) {
            throw new AspMappingException("Cannot map from term of type " + term.getClass().getSimpleName()
                    + " to Integer, expecting a ConstantTerm!");
        }
        return Integer.valueOf(((ConstantTerm<?>) term).getValue().toString());

    }

}
