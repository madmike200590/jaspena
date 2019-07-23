package io.github.madmike200590.jaspena.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractAnswerSetCollector implements Callable<Iterable<Set<String>>> {

    private static final Logger     LOGGER = LoggerFactory.getLogger(AbstractAnswerSetCollector.class);

    private final InputStream       solverOutput;
    private final Predicate<String> atomFilter;

    public AbstractAnswerSetCollector(InputStream is, Predicate<String> atomFilter) {
        this.solverOutput = is;
        this.atomFilter = atomFilter;
    }

    public Iterable<Set<String>> call() throws IOException {
        List<Set<String>> retVal = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(this.solverOutput));
        String line;
        Set<String> currAnswerSet = null;
        Set<String> lineAtoms = null;
        while ((line = br.readLine()) != null) {
            LOGGER.debug("Collecting line: {}", line);
            if (this.isAnswerSetStart(line)) {
                LOGGER.debug("Found start of new answer set");
                if (currAnswerSet != null) {
                    retVal.add(currAnswerSet);
                }
                currAnswerSet = new HashSet<>();
            } else {
                lineAtoms = this.extractAtoms(line);
                if (!lineAtoms.isEmpty()) {
                    for(String atom : lineAtoms) {
                        if(this.atomFilter.test(atom)) {
                            currAnswerSet.add(atom);
                        }
                    }
                }
            }
        }
        retVal.add(currAnswerSet);
        br.close();
        return retVal;
    }

    protected abstract Set<String> extractAtoms(String line);

    protected abstract boolean isAnswerSetStart(String line);

}
