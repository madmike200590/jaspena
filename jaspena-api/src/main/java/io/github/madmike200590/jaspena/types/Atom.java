package io.github.madmike200590.jaspena.types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import io.github.madmike200590.jaspena.types.term.Term;
import io.github.madmike200590.jaspena.types.term.impl.ConstantTerm;

/**
 * An incomplete, very basic implementation of a ground atom as contained in
 * answer sets out of a solver. No support for nested terms, really only
 * supports constant arguments of predicates
 * 
 * @author michael
 *
 */
public class Atom {

    private final Predicate  predicate;
    private final List<Term> terms;    // TODO make this an array for fast
                                       // reads
    // TODO enum mapping!

    private Atom(Predicate predicate, List<Term> terms) {
        this.predicate = predicate;
        this.terms = terms;
    }

    public static Atom newInstance(Predicate pred, List<Term> terms) {
        if (pred.getArity() != terms.size()) {
            throw new IllegalArgumentException("Predicate " + pred.getSymbol() + " has arity " + pred.getArity()
                    + ", but " + terms.size() + " terms were supplied!");
        }
        return new Atom(pred, terms);
    }

    public static Atom newGroundInstance(String predicateSymbol, String... terms) {
        Predicate predicate = new Predicate(predicateSymbol, terms.length);
        return Atom.newGroundInstance(predicate, terms);
    }

    public static Atom newGroundInstance(Predicate pred, String... terms) {
        List<Term> termList = new ArrayList<>();
        for (String term : terms) {
            termList.add(new ConstantTerm<>(term));
        }
        return new Atom(pred, termList);
    }

    public static Atom newGroundInstance(Predicate pred, List<String> terms) {
        return new Atom(pred, terms.stream().map((str) -> new ConstantTerm<>(str)).collect(Collectors.toList()));
    }

    public Predicate getPredicate() {
        return this.predicate;
    }

    public List<Term> getTerms() {
        return Collections.unmodifiableList(this.terms);
    }

    @Override
    public String toString() {
        if (this.terms.isEmpty()) {
            return this.getPredicate().getSymbol();
        }
        return this.getPredicate().getSymbol() + "(" + StringUtils.join(this.terms, ",") + ")";
    }

}
