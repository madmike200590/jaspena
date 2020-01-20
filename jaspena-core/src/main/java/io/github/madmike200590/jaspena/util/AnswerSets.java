package io.github.madmike200590.jaspena.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.github.madmike200590.jaspena.asp.AnswerSet;
import io.github.madmike200590.jaspena.exception.AspDataIntegrityException;
import io.github.madmike200590.jaspena.exception.AspMappingException;
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

    public static String asProgram(AnswerSet answerSet) {
        StringBuilder bld = new StringBuilder();
        Set<String> answerSetAtoms = AnswerSets.toStringSet(answerSet);
        for (String atom : answerSetAtoms) {
            bld.append(atom).append(".").append("\n");
        }
        return bld.toString();
    }

    public static boolean containsPredicate(AnswerSet answerSet, Predicate predicate) {
        return answerSet.getPredicates().contains(predicate);
    }

    public static boolean containsPredicate(AnswerSet answerSet, String symbol, int arity) {
        return AnswerSets.containsPredicate(answerSet, new Predicate(symbol, arity));
    }

    public static int numInstancesOf(AnswerSet answerSet, Predicate predicate) {
        if (!AnswerSets.containsPredicate(answerSet, predicate)) {
            return 0;
        }
        return answerSet.getAtomsForPredicate(predicate).size();
    }

    public static int numInstancesOf(AnswerSet answerSet, String symbol, int arity) {
        return AnswerSets.numInstancesOf(answerSet, new Predicate(symbol, arity));
    }

    public static List<Atom> listInstancesOf(AnswerSet answerSet, Predicate predicate) {
        if (!AnswerSets.containsPredicate(answerSet, predicate)) {
            return Collections.emptyList();
        }
        List<Atom> retVal = new ArrayList<>();
        retVal.addAll(answerSet.getAtomsForPredicate(predicate));
        return retVal;
    }

    public static List<Atom> listInstancesOf(AnswerSet answerSet, String symbol, int arity) {
        return AnswerSets.listInstancesOf(answerSet, new Predicate(symbol, arity));
    }

    /**
     * Fetches a single instance of the given predicate from the given answer
     * set. It is expected that the answer set contains exactly one instance of
     * the given predicate, if there are more or no instances, an
     * AspDataIntegrityException is thrown.
     * 
     * @param answerSet
     * @param predicate
     * @return the single instance of the given predicate in the answer set
     * @throws AspDataIntegrityException
     *             if no or more instances of the given predicate are contained
     */
    public static Atom getAtom(AnswerSet answerSet, Predicate predicate) throws AspMappingException {
        List<Atom> atoms = AnswerSets.listInstancesOf(answerSet, predicate);
        int instances = atoms.size();
        if (instances != 1) {
            throw new AspMappingException("Expected exactly one instance of predicate " + predicate.getSymbol()
                    + "/" + predicate.getArity() + ", but found " + instances);
        }
        return atoms.get(0);
    }

    public static Atom getAtom(AnswerSet answerSet, String symbol, int arity) throws AspMappingException {
        return AnswerSets.getAtom(answerSet, new Predicate(symbol, arity));
    }

}
