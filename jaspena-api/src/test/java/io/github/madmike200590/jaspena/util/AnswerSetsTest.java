package io.github.madmike200590.jaspena.util;

import org.junit.Assert;
import org.junit.Test;

public class AnswerSetsTest {
    
    @Test
    public void testExtractPredicateSymbol() {
        String a1 = AnswerSets.extractPredicateSymbol("a");
        String a2 = AnswerSets.extractPredicateSymbol("b(x,y)");
        String a3 = AnswerSets.extractPredicateSymbol("some_long_name(of,a,complicated,predicate(x))");
        Assert.assertEquals("a", a1);
        Assert.assertEquals("b", a2);
        Assert.assertEquals("some_long_name", a3);
    }

}
