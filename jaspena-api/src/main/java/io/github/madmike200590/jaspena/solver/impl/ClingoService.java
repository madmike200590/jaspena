package io.github.madmike200590.jaspena.solver.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
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
    private ExecutorService     streamCollectors         = Executors.newFixedThreadPool(2);

    @Override
    public synchronized Stream<Set<String>> solve(String program, Predicate<String> filter, int numAnswerSets)
            throws AspSolverException {
        Process proc = null;
        try {
            proc = this.startClingo(numAnswerSets);
        } catch (IOException ex) {
            LOGGER.error("Failed starting clingo process!", ex);
            throw new AspSolverException(
                    "Failed starting clingo process! (nested exception is: " + ex.getMessage() + ")");
        }
        InputStream errStream = proc.getErrorStream();
        InputStream outStream = proc.getInputStream();
        OutputStream procInput = proc.getOutputStream();

        Future<String> procStdErrFuture = this.streamCollectors.submit(new InputStreamCollector(errStream));
        Future<Iterable<Set<String>>> procStdOutFuture = this.streamCollectors.submit(new ClingoAnswerSetCollector(outStream));

        PrintStream ps = new PrintStream(procInput);
        ps.println(program);
        ps.close();

        int clingoExitCode = -1;
        try {
            clingoExitCode = proc.waitFor();
        } catch (InterruptedException ex) {
            LOGGER.error("Interrupted while waiting for clingo..", ex);
            throw new AspSolverException("Interrupted while waiting for Clingo!");
        }

        Iterable<Set<String>> clingoOut = null;
        String clingoErr = null;
        try {
            clingoErr = procStdErrFuture.get();
            clingoOut = procStdOutFuture.get();
        } catch (InterruptedException | ExecutionException ex) {
            LOGGER.error("Exception collecting process out/err streams", ex);
            throw new AspSolverException("Failed to obtain process output from Clingo!");
        }

        if (clingoExitCode == CLINGO_EXIT_INPUT_ERR
                || clingoExitCode == CLINGO_EXIT_INTERRUPTED1 || clingoExitCode == CLINGO_EXIT_INTERRUPTED2
                || clingoExitCode == CLINGO_EXIT_INTERRUPTED3) {
            throw new AspSolverException(
                    "Clingo exited abnormally, exit code = " + clingoExitCode + "\nstderr:\n" + clingoErr);
        }

        return StreamSupport.stream(clingoOut.spliterator(), false);
    }

    private Process startClingo(int numAnswerSets) throws IOException {
        return Runtime.getRuntime().exec(new String[] { this.clingoCommand, "-n", Integer.toString(numAnswerSets) });
    }

}
