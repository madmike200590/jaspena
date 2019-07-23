package io.github.madmike200590.jaspena.parsing;

import java.util.Set;

import io.github.madmike200590.jaspena.types.AnswerSet;

public interface IAnswerSetParser {
    
    AnswerSet parse(Set<String> atoms);

}
