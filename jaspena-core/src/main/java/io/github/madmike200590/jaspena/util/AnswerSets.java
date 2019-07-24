package io.github.madmike200590.jaspena.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.github.madmike200590.jaspena.types.AnswerSet;
import io.github.madmike200590.jaspena.types.Atom;
import io.github.madmike200590.jaspena.types.Predicate;

public final class AnswerSets {

    private AnswerSets() {

    }

    public static final Pattern BASIC_ATOM_REGEX          = Pattern.compile(
            "-?[a-z][a-zA-Z0-9_]*(\\(([a-z][a-zA-Z0-9_]*|[0-9]+)(,([a-z][a-zA-Z0-9_]*|[0-9]+))*\\))?");

    public static final Pattern TERMS_OF_BASIC_ATOM_REGEX = Pattern
            .compile("\\(([a-z][a-zA-Z0-9_]*|[0-9]+)(,([a-z][a-zA-Z0-9_]*|[0-9]+))*\\)");

    /**
     * Extract the predicate name from a positive atom
     * 
     * @param atom
     *            the atom to extract the predicate name from
     * @return the name of the predicate
     */
    public static String extractPredicateSymbol(String atom) {
        // FIXME this is really just a quick and dirty hack!
        return atom.split("\\(")[0];
    }

    public static List<String> extractTerms(String atom) {
        // FIXME dirty hack, in the long run we wanna do proper parsing
        Matcher termsMatcher = TERMS_OF_BASIC_ATOM_REGEX.matcher(atom);
        if (!termsMatcher.find()) {
            return Collections.emptyList();
        }
        String tmpTerms = termsMatcher.group();
        String terms = tmpTerms.substring(1, tmpTerms.length() - 1);
        return Arrays.asList(terms.split(","));
    }

    public static Set<String> toStringSet(AnswerSet answerSet) {
        Set<String> retVal = new HashSet<>();
        for (Predicate pred : answerSet.getPredicates()) {
            for (Atom atom : answerSet.getAtomsForPredicate(pred)) {
                retVal.add(atom.toString());
            }
        }
        return retVal;
    }

}
