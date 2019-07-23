package io.github.madmike200590.jaspena.util;

import java.util.function.Predicate;

public final class Filters {
    
    private Filters() {
        
    }

    public static Predicate<String> predicateSymbolIn(final String... symbols){
        return (atom) -> {
            String predName = AnswerSets.extractPredicateSymbol(atom);
            for(String symbol : symbols) {
                if(symbol.equals(predName)) {
                    return true;
                }
            }
            return false;
        };
    }
    
}
