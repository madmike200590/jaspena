package io.github.madmike200590.jaspena.solver.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import io.github.madmike200590.jaspena.exception.AspSolverException;
import io.github.madmike200590.jaspena.solver.IAspSolverService;

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
        Stream<Set<String>> answerSets = this.solverService.solve(aspProg, 0);
        List<Set<String>> answerSetList = answerSets.collect(Collectors.toList());
        Assert.assertEquals(1, answerSetList.size());
        Set<String> answer = answerSetList.get(0);
        Assert.assertTrue(answer.contains("a"));
    }

    @Test
    public void testEmptySet() throws AspSolverException {
        String aspProg = "{a}.";
        Stream<Set<String>> answerSets = this.solverService.solve(aspProg, 0);
        List<Set<String>> answerSetList = answerSets.collect(Collectors.toList());
        Assert.assertEquals(2, answerSetList.size());
        for (Set<String> answer : answerSetList) {
            Assert.assertTrue(answer.contains("a") || answer.isEmpty());
        }
    }

}
