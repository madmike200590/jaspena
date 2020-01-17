package io.github.madmike200590.jaspena.parsing.impl;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import io.github.madmike200590.jaspena.asp.AnswerSet;

public class BasicAnswerSetParserTest {

    @Test
    public void smokeTest() {
        Set<String> someAtoms = new HashSet<>();
        someAtoms.add("a");
        someAtoms.add("b");
        someAtoms.add("p(q)");
        someAtoms.add("x_y(1,2)");
        someAtoms.add("a_bc(xy,1,2,3)");
        AnswerSet answerSet = new BasicAnswerSetParser().parse(someAtoms);
        System.out.println(answerSet.toString());
    }

}
