package io.github.madmike200590.jaspena.core;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.madmike200590.jaspena.annotations.Atom;
import io.github.madmike200590.jaspena.core.reflect.ReflectionUtils;
import io.github.madmike200590.jaspena.exception.AspMappingException;
import io.github.madmike200590.jaspena.mapper.IAnswerSetToObjectMapper;

public class AnswerSetToObjectMapperFactory {

    private static final Logger                        LOGGER              = LoggerFactory
            .getLogger(AnswerSetToObjectMapperFactory.class);

    private final Map<Class<?>, Map<String, AtomInfo>> classToAtomMappings = new HashMap<>();
    private final Map<Class<?>, IAtomDataExtractor<?>> atomDataExtractors  = new HashMap<>();

    public AnswerSetToObjectMapperFactory() {
        this.atomDataExtractors.put(String.class, AtomDataExtractors::atomToString);
        this.atomDataExtractors.put(Integer.class, AtomDataExtractors::atomToInteger);
        this.atomDataExtractors.put(int.class, AtomDataExtractors::atomToInteger);
        this.atomDataExtractors.put(Boolean.class, AtomDataExtractors::atomToBoolean);
        this.atomDataExtractors.put(boolean.class, AtomDataExtractors::atomToBoolean);
    }

    @SuppressWarnings("unchecked")
    public <T> IAnswerSetToObjectMapper<T> createMapperFor(Class<T> clazz) throws AspMappingException {
        if (!this.classToAtomMappings.containsKey(clazz)) {
            this.register(clazz);
        }
        return (IAnswerSetToObjectMapper<T>) Proxy.newProxyInstance(
                AnswerSetToObjectMapperFactory.class.getClassLoader(),
                new Class<?>[] { IAnswerSetToObjectMapper.class },
                new InvocationHandler() {

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        LOGGER.debug("Called method: {}", method.getName());
                        return null;
                    }
                });
    }

    public void register(Class<?> clazz) throws AspMappingException {
        List<Field> atomProperties = ReflectionUtils.getFieldsAnnotatedWith(clazz, Atom.class);
        if (atomProperties.isEmpty()) {
            throw new IllegalArgumentException("Type " + clazz.getSimpleName() + " has no annotated fields!");
        }
        Map<String, AtomInfo> atomMappings = new LinkedHashMap<>();
        AtomInfo tmpAtomInfo;
        String tmpAtomName;
        Atom tmpAnnotation;
        Class<?> tmpTargetType;
        IAtomDataExtractor<?> tmpDataConverter;
        for (Field fld : atomProperties) {
            tmpAnnotation = fld.getAnnotation(Atom.class);
            tmpAtomName = tmpAnnotation.name();
            tmpTargetType = fld.getType();
            tmpDataConverter = this.atomDataExtractors.get(tmpTargetType);
            if (tmpDataConverter == null) {
                throw new AspMappingException(
                        "No data extractor found for target type: " + tmpTargetType.getSimpleName());
            }
            tmpAtomInfo = new AtomInfo();
            tmpAtomInfo.setName(tmpAtomName);
            tmpAtomInfo.setTargetType(tmpTargetType);
            tmpAtomInfo.setTargetField(fld);
            tmpAtomInfo.setAspDataConverter(tmpDataConverter);
            atomMappings.put(tmpAtomName, tmpAtomInfo);
        }
        this.classToAtomMappings.put(clazz, atomMappings);
    }

}
