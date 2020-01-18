package io.github.madmike200590.jaspena.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.madmike200590.jaspena.types.Atom;
import io.github.madmike200590.jaspena.types.Predicate;
import io.github.madmike200590.jaspena.types.term.Term;
import io.github.madmike200590.jaspena.types.term.impl.ConstantTerm;

/**
 * A query for ASP atoms matching a sequence of filter predicates
 * 
 * @author michael
 *
 */
public final class AtomQuery {

    private final Predicate                                  predicate;
    private Map<Integer, java.util.function.Predicate<Term>> filters = new HashMap<>();

    private AtomQuery(Predicate pred) {
        this.predicate = pred;
    }

    public static AtomQuery forPredicate(Predicate predicate) {
        return new AtomQuery(predicate);
    }

    public AtomQuery withFilter(int termIdx, java.util.function.Predicate<Term> filter) {
        if (termIdx >= this.predicate.getArity()) {
            throw new IndexOutOfBoundsException("Predicate " + this.predicate.getSymbol() + " has arity "
                    + this.predicate.getArity() + ", term index " + termIdx + " is invalid!");
        }
        if (this.filters.containsKey(termIdx)) {
            java.util.function.Predicate<Term> currFilter = this.filters.get(termIdx);
            this.filters.put(termIdx, currFilter.and(filter));
        } else {
            this.filters.put(termIdx, filter);
        }
        return this;
    }

    public AtomQuery withConstantEquals(int termIdx, String str) {
        return this.withFilter(termIdx, AtomQuery.constantTermEquals(str));
    }

    /**
     * Applies this query to an iterable of Atoms. Filters are worked off in
     * order of ascending term index in a conjunctive fashion, i.e. for an atom
     * to match the query, all of its terms must satisfy all filters on these
     * terms
     * 
     * @param atoms
     *            the atoms on which to apply the query
     * @return a list of atoms satisfying this query
     */
    public List<Atom> applyTo(Iterable<Atom> atoms) {
        List<Atom> atomsMatchingQuery = new ArrayList<>();
        java.util.function.Predicate<Term> currTermTester;
        boolean atomMatches;
        for (Atom atom : atoms) {
            if (!atom.getPredicate().equals(this.predicate)) {
                continue;
            }
            atomMatches = true;
            for (int i = 0; i < atom.getTerms().size(); i++) {
                if (this.filters.containsKey(i)) {
                    currTermTester = this.filters.get(i);
                    atomMatches &= currTermTester.test(atom.getTerms().get(i));
                }
            }
            if (atomMatches) {
                atomsMatchingQuery.add(atom);
            }
        }
        return atomsMatchingQuery;
    }

    private static java.util.function.Predicate<Term> constantTermEquals(final String str) {
        java.util.function.Predicate<Term> equalsGivenString = (t) -> {
            return AtomQuery.constantTermEquals(t, str);
        };
        return equalsGivenString;
    }

    private static boolean constantTermEquals(Term term, String str) {
        if (!(term instanceof ConstantTerm<?>)) {
            return false;
        }
        return ((ConstantTerm<?>) term).getValue().toString().equals(str);
    }

}
