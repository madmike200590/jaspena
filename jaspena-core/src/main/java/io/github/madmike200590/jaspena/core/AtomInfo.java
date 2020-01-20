package io.github.madmike200590.jaspena.core;

import java.lang.reflect.Method;

public class AtomInfo {

    private String                name;
    private int                   arity;
    private IAtomDataExtractor<?> aspDataConverter;
    private Class<?>              targetType;
    private Method                setterMethod;

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

    public Method getSetterMethod() {
        return this.setterMethod;
    }

    public void setSetterMethod(Method setterMethod) {
        this.setterMethod = setterMethod;
    }

    public int getArity() {
        return this.arity;
    }

    public void setArity(int arity) {
        this.arity = arity;
    }

}
