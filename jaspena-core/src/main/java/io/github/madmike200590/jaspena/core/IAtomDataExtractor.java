package io.github.madmike200590.jaspena.core;

import io.github.madmike200590.jaspena.exception.AspMappingException;
import io.github.madmike200590.jaspena.types.Atom;

@FunctionalInterface
public interface IAtomDataExtractor<T> {

    public T extractFromAtom(Atom atom) throws AspMappingException;

}
