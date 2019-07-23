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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.arity;
        result = prime * result + ((this.symbol == null) ? 0 : this.symbol.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Predicate)) {
            return false;
        }
        Predicate other = (Predicate) obj;
        if (this.arity != other.arity) {
            return false;
        }
        if (this.symbol == null) {
            if (other.symbol != null) {
                return false;
            }
        } else if (!this.symbol.equals(other.symbol)) {
            return false;
        }
        return true;
    }

}
