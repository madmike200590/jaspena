package io.github.madmike200590.jaspena.util;

public final class AnswerSets {

    private AnswerSets() {
        
    }
    
    /**
     * Extract the predicate name from a positive atom
     * @param atom the atom to extract the predicate name from
     * @return the name of the predicate
     */
    public static String extractPredicateSymbol(String atom) {
        // FIXME this is really just a quick and dirty hack!
        return atom.split("\\(")[0];
    }
    
}
