package io.github.madmike200590.jaspena.solver.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.madmike200590.jaspena.exception.AspSolverException;
import io.github.madmike200590.jaspena.parsing.IAnswerSetParser;
import io.github.madmike200590.jaspena.parsing.impl.BasicAnswerSetParser;
import io.github.madmike200590.jaspena.types.AnswerSet;
import io.github.madmike200590.jaspena.util.ClingoAnswerSetCollector;
import io.github.madmike200590.jaspena.util.InputStreamCollector;

public class ClingoService extends AbstractAspSolverService {

    private static final Logger LOGGER                   = LoggerFactory.getLogger(ClingoService.class);

    private static final int    CLINGO_EXIT_INPUT_ERR    = 128;
    private static final int    CLINGO_EXIT_INTERRUPTED1 = 1;
    private static final int    CLINGO_EXIT_INTERRUPTED2 = 11;
    private static final int    CLINGO_EXIT_INTERRUPTED3 = 31;

    public static final int     ALL_ANSWER_SETS          = 0;

    private String              clingoCommand            = "clingo";
    private ExecutorService     streamCollectors;
    private IAnswerSetParser    answerSetParser          = new BasicAnswerSetParser();

    private boolean             randomize;

    public ClingoService(boolean randomize) {
        this.randomize = randomize;
    }

    @Override
    public synchronized Stream<AnswerSet> solve(String program, Predicate<String> filter, int numAnswerSets)
            throws AspSolverException {
        Process proc = null;
        this.streamCollectors = Executors.newFixedThreadPool(2);
        try {
            proc = this.startClingo(numAnswerSets);
        } catch (IOException ex) {
            LOGGER.error("Failed starting clingo process!", ex);
            this.bailOut(
                    "Failed starting clingo process! (nested exception is: " + ex.getMessage() + ")");
        }
        InputStream errStream = proc.getErrorStream();
        InputStream outStream = proc.getInputStream();
        OutputStream procInput = proc.getOutputStream();

        Future<String> procStdErrFuture = this.streamCollectors.submit(new InputStreamCollector(errStream));
        Future<Iterable<Set<String>>> procStdOutFuture = this.streamCollectors
                .submit(new ClingoAnswerSetCollector(outStream, filter));

        PrintStream ps = new PrintStream(procInput);
        ps.println(program);
        ps.close();

        int clingoExitCode = -1;
        try {
            clingoExitCode = proc.waitFor();
        } catch (InterruptedException ex) {
            LOGGER.error("Interrupted while waiting for clingo..", ex);
            this.bailOut("Interrupted while waiting for Clingo!");
        }

        Iterable<Set<String>> clingoOut = null;
        String clingoErr = null;
        try {
            clingoErr = procStdErrFuture.get();
            clingoOut = procStdOutFuture.get();
        } catch (InterruptedException | ExecutionException ex) {
            LOGGER.error("Exception collecting process out/err streams", ex);
            this.bailOut("Failed to obtain process output from Clingo!");
        }

        if (clingoExitCode == CLINGO_EXIT_INPUT_ERR
                || clingoExitCode == CLINGO_EXIT_INTERRUPTED1 || clingoExitCode == CLINGO_EXIT_INTERRUPTED2
                || clingoExitCode == CLINGO_EXIT_INTERRUPTED3) {
            this.bailOut(
                    "Clingo exited abnormally, exit code = " + clingoExitCode + "\nstderr:\n" + clingoErr);
        }
        this.streamCollectors.shutdown();
        List<AnswerSet> answerSets = new ArrayList<>();
        for (Set<String> answerSet : clingoOut) {
            answerSets.add(this.answerSetParser.parse(answerSet));
        }
        return StreamSupport.stream(answerSets.spliterator(), false);
    }

    private Process startClingo(int numAnswerSets) throws IOException {
        Process retVal = null;
        if (this.randomize) {
            retVal = Runtime.getRuntime().exec(new String[] { this.clingoCommand, "-n", Integer.toString(numAnswerSets),
                    "--sign-def=rnd", "--seed=" + System.currentTimeMillis() / 1000 });
        } else {
            retVal = Runtime.getRuntime()
                    .exec(new String[] { this.clingoCommand, "-n", Integer.toString(numAnswerSets) });
        }
        return retVal;
    }

    private void bailOut(String errMsg) throws AspSolverException {
        this.streamCollectors.shutdown();
        throw new AspSolverException(errMsg);
    }

}
