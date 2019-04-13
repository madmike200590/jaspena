package io.github.madmike200590.jaspena.types;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Predicate {

    private final String     symbol;
    private final int        arity;
    private final List<Term> terms;

    public Predicate(String symbol, Term... terms) {
        this.symbol = symbol;
        this.terms = Arrays.asList(terms);
        this.arity = this.terms.size();
    }

    public String getSymbol() {
        return this.symbol;
    }

    public int getArity() {
        return this.arity;
    }

    public List<Term> getTerms() {
        return Collections.unmodifiableList(this.terms);
    }

}
