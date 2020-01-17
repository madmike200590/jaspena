package io.github.madmike200590.jaspena.mapper;

import io.github.madmike200590.jaspena.asp.AnswerSet;
import io.github.madmike200590.jaspena.exception.AspMappingException;

public interface IAnswerSetToObjectMapper<T> {
    
    T mapFromAnswerSet(AnswerSet answerSet) throws AspMappingException;

}
