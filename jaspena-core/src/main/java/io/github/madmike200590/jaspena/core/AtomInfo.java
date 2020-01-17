package io.github.madmike200590.jaspena.core;

import java.util.function.Function;

import io.github.madmike200590.jaspena.types.Atom;

public class AtomInfo {

    private String                 name;
    private Function<Atom, Object> aspDataConverter;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Function<Atom, Object> getAspDataConverter() {
        return this.aspDataConverter;
    }

    public void setAspDataConverter(Function<Atom, Object> aspDataConverter) {
        this.aspDataConverter = aspDataConverter;
    }

}
