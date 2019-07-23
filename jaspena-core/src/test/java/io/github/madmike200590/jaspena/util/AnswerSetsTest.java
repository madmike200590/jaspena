package io.github.madmike200590.jaspena.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

    @Test
    public void testExtractTerms() {
        String atom = "atom(aTerm,anotherTerm,1,2,3,1234,oneLastTerm,666)";
        List<String> extractedTerms = AnswerSets.extractTerms(atom);
        List<String> refTerms = Arrays
                .asList(new String[] { "aTerm", "anotherTerm", "1", "2", "3", "1234", "oneLastTerm", "666" });
        Assert.assertEquals(refTerms, extractedTerms);
    }

    @Test
    public void testExtractTermsZeroArity() {
        String atom = "aPredicateWithArityZero";
        Assert.assertEquals(Collections.emptyList(), AnswerSets.extractTerms(atom));
    }

}
