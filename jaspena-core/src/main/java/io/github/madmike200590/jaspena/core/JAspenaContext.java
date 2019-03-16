/**
 * 
 */
package io.github.madmike200590.jaspena.core;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.madmike200590.jaspena.annotations.AspPredicate;
import io.github.madmike200590.jaspena.core.exception.JAspenaContextException;
import io.github.madmike200590.jaspena.core.reflect.ReflectionUtils;

/**
 * @author Michael Langowski, e1426581@student.tuwien.ac.at
 *
 */
public class JAspenaContext {

    private static final Logger                LOGGER              = LoggerFactory.getLogger(JAspenaContext.class);

    private Set<Class<?>>                      knownPredicateTypes = new HashSet<>();
    private Map<Class<?>, Map<String, Method>> getterRegistry      = new HashMap<>();
    // TODO setters!

    public synchronized void registerPredicateType(Class<?> clazz) throws JAspenaContextException {
        if (this.knownPredicateTypes.contains(clazz)) {
            LOGGER.info("Type " + clazz.getName() + " already registered, not doing it again!");
            return;
        }
        if (!clazz.isAnnotationPresent(AspPredicate.class)) {
            throw new JAspenaContextException(
                    "Cannot register type " + clazz.getSimpleName() + " as ASP predicate, not annotated as such!");
        }
        List<Field> aspFields = ReflectionUtils.getAspFieldsForType(clazz);
        Map<String, Method> getters = new HashMap<>();
        try {
            for (Field fld : aspFields) {
                getters.put(fld.getName(), ReflectionUtils.getGetterForField(fld));
            }
        } catch (NoSuchMethodException ex) {
            throw new JAspenaContextException(
                    "Registration failed because no suitable getter method exists. Nested exception: "
                            + ex.getMessage());
        } catch (SecurityException ex) {
            throw new JAspenaContextException("Security exception while rregistering predicate: " + ex.getMessage());
        }
        this.getterRegistry.put(clazz, getters);
        this.knownPredicateTypes.add(clazz);
    }

    public Set<Class<?>> getKnownPredicateTypes() {
        return Collections.unmodifiableSet(this.knownPredicateTypes);
    }
}
