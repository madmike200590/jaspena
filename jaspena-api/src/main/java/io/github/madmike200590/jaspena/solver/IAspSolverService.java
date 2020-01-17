package io.github.madmike200590.jaspena.solver;

import java.util.function.Predicate;
import java.util.stream.Stream;

import io.github.madmike200590.jaspena.asp.AnswerSet;
import io.github.madmike200590.jaspena.exception.AspSolverException;

public interface IAspSolverService {

    // TODO methods for firstAnswerSet and allAnswerSets
    public static final int DEFAULT_NUM_ANSWER_SETS = 1;

    Stream<AnswerSet> solve(String program) throws AspSolverException;

    Stream<AnswerSet> solve(String program, int numAnswerSets) throws AspSolverException;

    Stream<AnswerSet> solve(String program, Predicate<String> filter) throws AspSolverException;

    Stream<AnswerSet> solve(String program, Predicate<String> filter, int numAnswerSets) throws AspSolverException;

}
