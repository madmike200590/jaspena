package io.github.madmike200590.jaspena.types;

import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
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
