package io.github.madmike200590.jaspena.parsing.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.github.madmike200590.jaspena.exception.AspSyntaxException;
import io.github.madmike200590.jaspena.parsing.IAnswerSetParser;
import io.github.madmike200590.jaspena.types.AnswerSet;
import io.github.madmike200590.jaspena.types.Atom;
import io.github.madmike200590.jaspena.types.Predicate;
import io.github.madmike200590.jaspena.util.AnswerSets;

public class BasicAnswerSetParser implements IAnswerSetParser {

    @Override
    public AnswerSet parse(Set<String> atoms) {
        Map<Predicate, Set<Atom>> answerSet = new HashMap<>();
        String tmpPredicateSymbol;
        Predicate tmpPredicate;
        List<String> tmpTerms;
        Atom tmpAtom;
        for (String atomStr : atoms) {
            if (!AnswerSets.BASIC_ATOM_REGEX.matcher(atomStr).matches()) {
                throw new AspSyntaxException("String " + atomStr + " does not represent a valid ground atom!");
            }
            tmpPredicateSymbol = AnswerSets.extractPredicateSymbol(atomStr);
            tmpTerms = AnswerSets.extractTerms(atomStr);
            tmpPredicate = new Predicate(tmpPredicateSymbol, tmpTerms.size());
            tmpAtom = Atom.newInstance(tmpPredicate, tmpTerms);
            if (!answerSet.containsKey(tmpPredicate)) {
                answerSet.put(tmpPredicate, new HashSet<>());
            }
            answerSet.get(tmpPredicate).add(tmpAtom);
        }
        return new AnswerSet(answerSet);
    }

}
