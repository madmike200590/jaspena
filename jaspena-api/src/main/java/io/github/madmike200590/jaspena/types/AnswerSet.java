package io.github.madmike200590.jaspena.types;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class AnswerSet {

    private final Map<Predicate, Set<Atom>> atoms;

    private AnswerSet(Map<Predicate, Set<Atom>> atoms) {
        this.atoms = atoms;
    }

    public Set<Atom> getAtomsForPredicate(Predicate pred) {
        if (!this.atoms.containsKey(pred)) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(this.atoms.get(pred));
    }

}
