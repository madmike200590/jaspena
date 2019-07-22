package io.github.madmike200590.jaspena.solver;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

import io.github.madmike200590.jaspena.exception.AspSolverException;

public interface IAspSolverService {

    public static final int DEFAULT_NUM_ANSWER_SETS = 1;

    Stream<Set<String>> solve(String program) throws AspSolverException;

    Stream<Set<String>> solve(String program, int numAnswerSets) throws AspSolverException;

    Stream<Set<String>> solve(String program, Predicate<String> filter) throws AspSolverException;

    Stream<Set<String>> solve(String program, Predicate<String> filter, int numAnswerSets) throws AspSolverException;

}
