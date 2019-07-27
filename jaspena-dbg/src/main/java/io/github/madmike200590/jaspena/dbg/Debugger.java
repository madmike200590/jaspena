package io.github.madmike200590.jaspena.dbg;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import io.github.madmike200590.jaspena.solver.IAspSolverService;
import io.github.madmike200590.jaspena.solver.impl.ClingoService;
import io.github.madmike200590.jaspena.types.AnswerSet;
import io.github.madmike200590.jaspena.util.AnswerSets;
import io.github.madmike200590.jaspena.util.Programs;

public class Debugger {

    private static final Option  OPT_NUM_ANSWER_SETS = Option.builder("n").longOpt("numAS").hasArg(true)
            .argName("number").type(Integer.class)
            .desc("the number of answer sets to compute (default: compute all)").build();

    private static final Option  OPT_DBG_PROG        = Option.builder("d").longOpt("debug-program").hasArg(true)
            .argName("file").type(String.class)
            .desc("The program in the given file will be concatenated with every anwer set of the original program. Should only produce one answer set!")
            .build();

    private static final Options CLI_OPTS            = new Options();

    static {
        /*
         * Below code adds all options defined above to CLI_OPTS - needed for
         * parsing
         */
        CLI_OPTS.addOption(OPT_NUM_ANSWER_SETS);
        CLI_OPTS.addOption(OPT_DBG_PROG);
    }

    // TODO argument to choose solver!
    // FIXME proper arg parsing, proper exception handling
    public static void main(String[] args) throws Exception {
        int numAnswerSets = 1;
        String dbgCodeFile = null;

        CommandLine commandLine = new DefaultParser().parse(CLI_OPTS, args);
        if (commandLine.getArgs().length != 1) {
            throw new IllegalArgumentException(
                    "Expecting exactly one positional argument, namely a path to an ASP code file!");
        }

        for (Option opt : commandLine.getOptions()) {
            if (opt.getArgName().equals(OPT_NUM_ANSWER_SETS.getArgName())) {
                numAnswerSets = Integer.valueOf(opt.getValue());
            } else if (opt.getArgName().equals(OPT_DBG_PROG.getArgName())) {
                dbgCodeFile = opt.getValue();
            }
        }

        if (dbgCodeFile == null) {
            throw new IllegalArgumentException("need to specify a debug file!");
        }

        Path aspFile = Paths.get(commandLine.getArgs()[0]);
        Path dbgFile = Paths.get(dbgCodeFile);

        System.out.println("Debugging " + aspFile.toString() + " using dbg file " + dbgFile.toString());

        String asp = Programs.loadAspCode(aspFile);
        String debugAsp = Programs.loadAspCode(dbgFile);
        doDebugExecution(asp, debugAsp, numAnswerSets);
    }

    private static void doDebugExecution(String asp, String debugAsp, int numAnswerSets) throws Exception {
        IAspSolverService solverService = new ClingoService(false);
        Stream<AnswerSet> answerSets = solverService.solve(asp, numAnswerSets);
        List<AnswerSet> answerSetList = answerSets.collect(Collectors.toList());
        int cnt = 1;
        String resultProg;
        Stream<AnswerSet> debugResult;
        List<AnswerSet> dbgResultLst;
        for (AnswerSet as : answerSetList) {
            System.out.println("Got answer set " + cnt++ + " from solver:\n" + as);
            resultProg = AnswerSets.asProgram(as) + "\n" + debugAsp;
            System.out.println("\n\nSolving debug program:\n" + resultProg);
            debugResult = solverService.solve(resultProg);
            dbgResultLst = debugResult.collect(Collectors.toList());
            if (dbgResultLst.size() != 1) {
                throw new IllegalStateException("Expected only one answer set from debug program!");
            }
            System.out.println(
                    "Got debug result: \n" + dbgResultLst.get(0) + "\n\n****************************************\n");
        }
    }

}
