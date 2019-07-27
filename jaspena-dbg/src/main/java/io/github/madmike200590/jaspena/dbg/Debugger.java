package io.github.madmike200590.jaspena.dbg;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

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
    public static void main(String[] args) throws ParseException {
        int numAnswerSets;
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

        Path aspFile = Paths.get(commandLine.getArgs()[0]);
        Path dbgFile = Paths.get(dbgCodeFile);

        System.out.println("Debugging " + aspFile.toString() + " using dbg file " + dbgFile.toString());

    }

}
