package io.github.madmike200590.jaspena.core;

import java.lang.reflect.Field;
import java.util.function.Function;

import io.github.madmike200590.jaspena.types.Atom;

public class AtomInfo {

    private String                name;
    private IAtomDataExtractor<?> aspDataConverter;
    private Class<?>              targetType;
    private Field                 targetField;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IAtomDataExtractor<?> getAspDataConverter() {
        return this.aspDataConverter;
    }

    public void setAspDataConverter(IAtomDataExtractor<?> aspDataConverter) {
        this.aspDataConverter = aspDataConverter;
    }

    public Class<?> getTargetType() {
        return this.targetType;
    }

    public void setTargetType(Class<?> targetType) {
        this.targetType = targetType;
    }

    public Field getTargetField() {
        return this.targetField;
    }

    public void setTargetField(Field targetField) {
        this.targetField = targetField;
    }

}
