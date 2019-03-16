/**
 * 
 */
package io.github.madmike200590.jaspena.mapper;

import io.github.madmike200590.jaspena.exception.AspMappingException;

/**
 * @author Michael Langowski, e1426581@student.tuwien.ac.at
 *
 */
public interface AspObjectMapper<T> {

    T fromAsp(String aspAtom) throws AspMappingException;

    String toAspAtom(T instance) throws AspMappingException;

}
