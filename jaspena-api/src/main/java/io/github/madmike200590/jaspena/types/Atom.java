package io.github.madmike200590.jaspena.types;

public class Atom {

    private final Predicate predicate;

    public Atom(Predicate predicate) {
        this.predicate = predicate;
    }

    public Predicate getPredicate() {
        return this.predicate;
    }

}
