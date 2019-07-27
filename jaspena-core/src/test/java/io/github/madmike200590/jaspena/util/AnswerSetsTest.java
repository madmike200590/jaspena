package io.github.madmike200590.jaspena.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Test;

import io.github.madmike200590.jaspena.parsing.impl.BasicAnswerSetParser;
import io.github.madmike200590.jaspena.types.AnswerSet;

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

    @Test
    public void testAnswerSetToProgram() {
        Set<String> atoms = new TreeSet<>();
        atoms.add("a(a)");
        atoms.add("a(b)");
        atoms.add("some_shit(of,1)");
        atoms.add("x(a)");
        atoms.add("x(y,z)");
        AnswerSet as = new BasicAnswerSetParser().parse(atoms);
        String prog = AnswerSets.asProgram(as);
        String expectedProg = "a(b).\n"
                + "x(y,z).\n"
                + "a(a).\n"
                + "x(a).\n"
                + "some_shit(of,1).\n";
        Assert.assertEquals(expectedProg, prog);
    }

}
