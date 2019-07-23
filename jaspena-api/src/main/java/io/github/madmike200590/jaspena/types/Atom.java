package io.github.madmike200590.jaspena.types;

import java.util.Collections;
import java.util.List;

/**
 * An incomplete, very basic implementation of a ground atom as contained in
 * answer sets out of a solver. No support for nested terms, really only
 * supports constant arguments of predicates
 * 
 * @author michael
 *
 */
public class Atom {

    private final Predicate    predicate;
    private final List<String> terms;

    private Atom(Predicate predicate, List<String> terms) {
        this.predicate = predicate;
        this.terms = terms;
    }

    public static Atom newInstance(Predicate pred, List<String> terms) {
        if (pred.getArity() != terms.size()) {
            throw new IllegalArgumentException("Predicate " + pred.getSymbol() + " has arity " + pred.getArity()
                    + ", but " + terms.size() + " terms were supplied!");
        }
        return new Atom(pred, terms);
    }

    public Predicate getPredicate() {
        return this.predicate;
    }

    public List<String> getTerms() {
        return Collections.unmodifiableList(this.terms);
    }

}
