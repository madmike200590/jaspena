package io.github.madmike200590.jaspena.mapper;

import io.github.madmike200590.jaspena.exception.AspMappingException;
import io.github.madmike200590.jaspena.types.AnswerSet;

public interface IAnswerSetToObjectMapper<T> {
    
    T mapFromAnswerSet(AnswerSet answerSet) throws AspMappingException;

}
