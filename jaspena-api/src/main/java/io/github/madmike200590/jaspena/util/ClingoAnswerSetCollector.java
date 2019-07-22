package io.github.madmike200590.jaspena.util;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClingoAnswerSetCollector extends AbstractAnswerSetCollector {

    private static final Logger  LOGGER                       = LoggerFactory
            .getLogger(AbstractAnswerSetCollector.class);

    private static final Pattern CLINGO_ANSWERSET_START_REGEX = Pattern.compile("Answer: [0-9]+");
    private static final Pattern CLINGO_ATOMS_REGEX           = Pattern.compile(
            "-?[a-z][a-zA-Z0-9_]*(\\(([a-z][a-zA-Z0-9_]*|[0-9]+)(,([a-z][a-zA-Z0-9_]*|[0-9]+))*\\))?( -?[a-z][a-zA-Z0-9_]*(\\(([a-z][a-zA-Z0-9_]*|[0-9]+)(,([a-z][a-zA-Z0-9_]*|[0-9]+))*\\))?)*");

    public ClingoAnswerSetCollector(InputStream is) {
        super(is);
    }

    @Override
    protected Set<String> extractAtoms(String line) {
        String[] atoms = line.split(" ");
        Set<String> retVal = new HashSet<>();
        retVal.addAll(Arrays.asList(atoms));
        return retVal;
    }

    protected boolean isAnswerSetLine(String line) {
        boolean retVal = CLINGO_ATOMS_REGEX.matcher(line).matches();
        LOGGER.debug("line = {}, matches atoms regex? {}", line, retVal);
        return retVal;
    }

    @Override
    protected boolean isAnswerSetStart(String line) {
        return CLINGO_ANSWERSET_START_REGEX.matcher(line).matches();
    }

}
