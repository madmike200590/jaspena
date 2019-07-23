package io.github.madmike200590.jaspena.util;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClingoAnswerSetCollector extends AbstractAnswerSetCollector {

    @SuppressWarnings("unused")
    private static final Logger  LOGGER                       = LoggerFactory
            .getLogger(AbstractAnswerSetCollector.class);

    private static final Pattern CLINGO_ANSWERSET_START_REGEX = Pattern.compile("Answer: [0-9]+");

    public ClingoAnswerSetCollector(InputStream is, Predicate<String> filter) {
        super(is, filter);
    }

    @Override
    protected Set<String> extractAtoms(String line) {
        String[] atoms = line.split(" ");
        Set<String> retVal = new HashSet<>();
        for (String atom : atoms) {
            if (!AnswerSets.BASIC_ATOM_REGEX.matcher(atom).matches()) {
                return retVal;
            }
        }
        retVal.addAll(Arrays.asList(atoms));
        return retVal;
    }

    @Override
    protected boolean isAnswerSetStart(String line) {
        return CLINGO_ANSWERSET_START_REGEX.matcher(line).matches();
    }

}
