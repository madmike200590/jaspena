package io.github.madmike200590.jaspena.util;

import java.util.List;
import java.util.function.Function;

import io.github.madmike200590.jaspena.exception.AspDataIntegrityException;
import io.github.madmike200590.jaspena.types.Atom;
import io.github.madmike200590.jaspena.types.term.Term;
import io.github.madmike200590.jaspena.types.term.impl.ConstantTerm;

/**
 * Utility class providing convenience methods for working with Atoms of the
 * Alpha solver
 * 
 * @author michael
 *
 */
public final class Atoms {

    private Atoms() {

    }

    public static <T> T getTermValue(Atom atom, int idx, Function<ConstantTerm<?>, T> valueExtractor)
            throws AspDataIntegrityException {
        List<Term> terms = atom.getTerms();
        int arity = terms.size();
        if (idx >= arity) {
            throw new AspDataIntegrityException("Atom " + atom.toString() + "has no term at index " + idx);
        }
        Term term = terms.get(idx);
        if (!(term instanceof ConstantTerm<?>)) {
            throw new AspDataIntegrityException(
                    "Cannot get value for term - not a constant! (term = " + term.toString() + ")");
        }
        ConstantTerm<?> constantTerm = (ConstantTerm<?>) term;
        return valueExtractor.apply(constantTerm);
    }

    public static String getString(Atom atom, int idx) throws AspDataIntegrityException {
        return Atoms.getTermValue(atom, idx, Atoms::extractString);
    }

    public static int getInt(Atom atom, int idx) throws AspDataIntegrityException {
        try {
            return Atoms.getTermValue(atom, idx, Atoms::extractInt);
        } catch (NumberFormatException ex) {
            throw new AspDataIntegrityException("Failed to extract an integer from atom: " + ex.getMessage());
        }
    }

    private static int extractInt(ConstantTerm<?> term) {
        return Integer.valueOf(term.getValue().toString());
    }

    private static String extractString(ConstantTerm<?> term) {
        return term.getValue().toString();
    }

}
