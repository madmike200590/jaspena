package io.github.madmike200590.jaspena.asp;

import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

import io.github.madmike200590.jaspena.types.Atom;
import io.github.madmike200590.jaspena.types.Predicate;

import java.util.Set;

public class AnswerSet {

    private final Map<Predicate, Set<Atom>> atoms;

    public AnswerSet(Map<Predicate, Set<Atom>> atoms) {
        this.atoms = atoms;
    }

    public Set<Atom> getAtomsForPredicate(Predicate pred) {
        if (!this.atoms.containsKey(pred)) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(this.atoms.get(pred));
    }

    public Set<Atom> getAtomsForPredicate(String predSymbol, int arity) {
        return this.getAtomsForPredicate(new Predicate(predSymbol, arity));
    }

    /**
     * Fetches a single atom for the given predicate. If there is more than one
     * atom for that predicate, an exception is thrown. Likewise, an exception
     * is thrown when there are not atoms.
     * 
     * @param pred
     *            the predicate to look for
     * @return an Atom for the given predicate
     * @throws IllegalArgumentException
     *             if more than one or no atom exists for the given predicate
     */
    public Atom getAtomForPredicate(Predicate pred) {
        Set<Atom> atoms = this.getAtomsForPredicate(pred);
        if (atoms.isEmpty() || atoms.size() > 1) {
            throw new IllegalArgumentException("Illegal request - there are " + atoms.size()
                    + " instances of predicate " + pred.getSymbol() + "/" + pred.getArity());
        }
        return atoms.iterator().next();
    }

    public Atom getAtomForPredicate(String predName, int arity) {
        return this.getAtomForPredicate(new Predicate(predName, arity));
    }

    public int instancesOf(Predicate pred) {
        return this.getAtomsForPredicate(pred).size();
    }

    public int instancesOf(String predName, int arity) {
        return this.instancesOf(new Predicate(predName, arity));
    }

    public Set<Predicate> getPredicates() {
        if (this.atoms.isEmpty()) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(this.atoms.keySet());
    }

    @Override
    public String toString() {
        StringBuilder bld = new StringBuilder();
        bld.append("AnswerSet {").append(System.lineSeparator());
        for (Entry<Predicate, Set<Atom>> entry : this.atoms.entrySet()) {
            for (Atom atom : entry.getValue()) {
                bld.append("\t").append(atom.toString()).append(System.lineSeparator());
            }
        }
        bld.append("}");
        return bld.toString();
    }

}
