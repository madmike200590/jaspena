package io.github.madmike200590.jaspena.core;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.github.madmike200590.jaspena.annotations.Atom;
import io.github.madmike200590.jaspena.core.reflect.ReflectionUtils;
import io.github.madmike200590.jaspena.exception.AspMappingException;
import io.github.madmike200590.jaspena.mapper.IAnswerSetToObjectMapper;

public class AnswerSetToObjectMapperFactory {

    private final Map<Class<?>, Map<String, AtomInfo>> classToAtomMappings = new HashMap<>();
    private final Map<Class<?>, IAtomDataExtractor<?>> atomDataExtractors  = new HashMap<>();

    public AnswerSetToObjectMapperFactory() {
        this.atomDataExtractors.put(String.class, AtomDataExtractors::atomToString);
        this.atomDataExtractors.put(Integer.class, AtomDataExtractors::atomToInteger);
        this.atomDataExtractors.put(int.class, AtomDataExtractors::atomToInteger);
        this.atomDataExtractors.put(Boolean.class, AtomDataExtractors::atomToBoolean);
        this.atomDataExtractors.put(boolean.class, AtomDataExtractors::atomToBoolean);
    }

    public <T> IAnswerSetToObjectMapper<T> createMapperFor(Class<T> clazz) throws AspMappingException {
        if (!this.classToAtomMappings.containsKey(clazz)) {
            this.register(clazz);
        }
        return new ReflectiveAnswerSetToObjectMapper<>(clazz, this.classToAtomMappings.get(clazz));
    }

    public void register(Class<?> clazz) throws AspMappingException {
        List<Field> atomProperties = ReflectionUtils.getFieldsAnnotatedWith(clazz, Atom.class);
        if (atomProperties.isEmpty()) {
            throw new IllegalArgumentException("Type " + clazz.getSimpleName() + " has no annotated fields!");
        }
        Map<String, AtomInfo> atomMappings = new LinkedHashMap<>();
        AtomInfo tmpAtomInfo;
        for (Field fld : atomProperties) {
            tmpAtomInfo = this.buildAtomInfo(fld);
            atomMappings.put(tmpAtomInfo.getName(), tmpAtomInfo);
        }
        this.classToAtomMappings.put(clazz, atomMappings);
    }

    private AtomInfo buildAtomInfo(Field fld) throws AspMappingException {
        AtomInfo retVal;
        Atom annotation = fld.getAnnotation(Atom.class);
        String atomName = annotation.name();
        Class<?> targetType = fld.getType();
        IAtomDataExtractor<?> dataExtractor = this.atomDataExtractors.get(targetType);
        if (dataExtractor == null) {
            throw new AspMappingException(
                    "No data extractor found for target type: " + targetType.getSimpleName());
        }
        Method setterMethod = null;
        try {
            setterMethod = ReflectionUtils.getSetterForField(fld);
        } catch (NoSuchMethodException ex) {
            throw new AspMappingException("No setter method found for field " + fld.toString());
        }
        retVal = new AtomInfo();
        retVal.setName(atomName);
        retVal.setTargetType(targetType);
        if (targetType.equals(Boolean.class) || targetType.equals(boolean.class)) {
            retVal.setArity(0);
        } else {
            retVal.setArity(1);
        }
        retVal.setSetterMethod(setterMethod);
        retVal.setAspDataConverter(dataExtractor);
        return retVal;
    }

}
