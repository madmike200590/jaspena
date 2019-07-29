package io.github.madmike200590.jaspena.types;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

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
    private final List<String> terms;    // TODO make this an array for fast
                                         // reads
    // TODO enum mapping!

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

    @Override
    public String toString() {
        if (this.terms.isEmpty()) {
            return this.getPredicate().getSymbol();
        }
        return this.getPredicate().getSymbol() + "(" + StringUtils.join(this.terms, ",") + ")";
    }

    public int getInt(int idx) {
        return Integer.valueOf(this.terms.get(idx));
    }

    public String getString(int idx) {
        return this.terms.get(idx);
    }

}
