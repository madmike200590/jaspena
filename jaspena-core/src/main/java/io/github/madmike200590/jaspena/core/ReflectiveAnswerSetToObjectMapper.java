package io.github.madmike200590.jaspena.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.madmike200590.jaspena.asp.AnswerSet;
import io.github.madmike200590.jaspena.exception.AspMappingException;
import io.github.madmike200590.jaspena.mapper.IAnswerSetToObjectMapper;
import io.github.madmike200590.jaspena.types.Atom;
import io.github.madmike200590.jaspena.util.AnswerSets;

public class ReflectiveAnswerSetToObjectMapper<T> implements IAnswerSetToObjectMapper<T> {

    private static final Logger         LOGGER = LoggerFactory.getLogger(ReflectiveAnswerSetToObjectMapper.class);

    private final Class<T>              targetType;
    private final Map<String, AtomInfo> atomMappings;

    public ReflectiveAnswerSetToObjectMapper(Class<T> targetType, Map<String, AtomInfo> atomMappings) {
        this.targetType = targetType;
        this.atomMappings = atomMappings;
    }

    @Override
    public T mapFromAnswerSet(AnswerSet answerSet) throws AspMappingException {
        T retVal = null;
        try {
            retVal = this.targetType.newInstance();
            Atom srcAtom;
            AtomInfo atomInfo;
            Object extractedValue;
            Method setterMethod;
            for (Map.Entry<String, AtomInfo> entry : this.atomMappings.entrySet()) {
                atomInfo = entry.getValue();
                srcAtom = AnswerSets.getAtom(answerSet, entry.getKey(), atomInfo.getArity());
                extractedValue = atomInfo.getAspDataConverter().extractFromAtom(srcAtom);
                setterMethod = atomInfo.getSetterMethod();
                setterMethod.invoke(retVal, extractedValue);
            }
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException ex) {
            LOGGER.error("Error mapping answer set!", ex);
            throw new AspMappingException("Error mapping answer set!: " + ex.getMessage());
        }
        return retVal;
    }

}
