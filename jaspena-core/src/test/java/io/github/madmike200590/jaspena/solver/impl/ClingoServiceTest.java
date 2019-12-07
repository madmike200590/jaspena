package io.github.madmike200590.jaspena.solver.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import io.github.madmike200590.jaspena.exception.AspSolverException;
import io.github.madmike200590.jaspena.solver.IAspSolverService;
import io.github.madmike200590.jaspena.types.AnswerSet;
import io.github.madmike200590.jaspena.util.AnswerSets;

public class ClingoServiceTest {

    private IAspSolverService solverService = new ClingoService(false);

    @Test
    @Ignore
    public void smokeTest() throws AspSolverException {
        String aspProg = "1 {a(0..3)} 1.";
        this.solverService.solve(aspProg, 0);
    }

    @Test
    public void testSimpleAtom() throws AspSolverException {
        String aspProg = "a.";
        Stream<AnswerSet> answerSets = this.solverService.solve(aspProg, 0);
        List<AnswerSet> answerSetList = answerSets.collect(Collectors.toList());
        List<Set<String>> answerSetStringList = new ArrayList<>();
        for (AnswerSet answerSet : answerSetList) {
            answerSetStringList.add(AnswerSets.toStringSet(answerSet));
        }
        Assert.assertEquals(1, answerSetStringList.size());
        Set<String> answer = answerSetStringList.get(0);
        Assert.assertTrue(answer.contains("a"));
    }

    @Test
    public void testEmptySet() throws AspSolverException {
        String aspProg = "{a}.";
        Stream<AnswerSet> answerSets = this.solverService.solve(aspProg, 0);
        List<AnswerSet> answerSetList = answerSets.collect(Collectors.toList());
        List<Set<String>> answerSetStringList = new ArrayList<>();
        for (AnswerSet answerSet : answerSetList) {
            answerSetStringList.add(AnswerSets.toStringSet(answerSet));
        }
        Assert.assertEquals(2, answerSetStringList.size());
        for (Set<String> answer : answerSetStringList) {
            Assert.assertTrue(answer.contains("a") || answer.isEmpty());
        }
    }

    @Test
    public void testUnsatisfiable() throws AspSolverException {
        String aspProg = "a.\n:-a.";
        Stream<AnswerSet> answerSets = this.solverService.solve(aspProg, 0);
        Assert.assertTrue(answerSets.count() == 0);
    }
    
}
