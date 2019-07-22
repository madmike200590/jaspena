package io.github.madmike200590.jaspena.solver.impl;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

import io.github.madmike200590.jaspena.exception.AspSolverException;
import io.github.madmike200590.jaspena.solver.IAspSolverService;

abstract class AbstractAspSolverService implements IAspSolverService {

    @Override
    public Stream<Set<String>> solve(String program) throws AspSolverException {
        return this.solve(program, (s) -> {
            return true;
        }, IAspSolverService.DEFAULT_NUM_ANSWER_SETS);
    }

    @Override
    public Stream<Set<String>> solve(String program, int numAnswerSets) throws AspSolverException {
        return this.solve(program, (s) -> {
            return true;
        }, numAnswerSets);
    }

    @Override
    public Stream<Set<String>> solve(String program, Predicate<String> filter) throws AspSolverException {
        return this.solve(program, filter, IAspSolverService.DEFAULT_NUM_ANSWER_SETS);
    }

    @Override
    public abstract Stream<Set<String>> solve(String program, Predicate<String> filter, int numAnswerSets)
            throws AspSolverException;

}
