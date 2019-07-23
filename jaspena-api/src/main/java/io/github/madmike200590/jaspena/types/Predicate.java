package io.github.madmike200590.jaspena.types;

/**
 * TODO hashCode, equals!
 * 
 * @author michael
 *
 */
public class Predicate {

    private final String symbol;
    private final int    arity;

    public Predicate(String symbol, int arity) {
        this.symbol = symbol;
        this.arity = arity;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public int getArity() {
        return this.arity;
    }

}
