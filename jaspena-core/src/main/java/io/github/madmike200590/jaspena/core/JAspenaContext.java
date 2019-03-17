/**
 * 
 */
package io.github.madmike200590.jaspena.core;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.madmike200590.jaspena.annotations.AspPredicate;
import io.github.madmike200590.jaspena.core.exception.JAspenaContextException;
import io.github.madmike200590.jaspena.core.reflect.ReflectionUtils;

/**
 * @author Michael Langowski, e1426581@student.tuwien.ac.at
 *
 */
public final class JAspenaContext {

    private static final Logger                           LOGGER               = LoggerFactory
            .getLogger(JAspenaContext.class);

    private DecimalFormat                                 defaultDecimalFormat = new DecimalFormat("#.#####");

    private final Map<Class<?>, Function<Object, String>> defaultFieldToStringMappers;
    private final Map<Class<?>, Function<String, Object>> defaultStringToFieldMappers;

    private final Set<Class<?>>                           knownPredicateTypes  = Collections
            .synchronizedSet(new HashSet<>());
    private final Map<Class<?>, Map<String, Method>>      getterRegistry       = Collections
            .synchronizedMap(new HashMap<>());
    private final Map<Class<?>, Map<String, Method>>      setterRegistry       = Collections
            .synchronizedMap(new HashMap<>());

    private JAspenaContext() {
        this.defaultFieldToStringMappers = new HashMap<>();
        this.defaultStringToFieldMappers = new HashMap<>();
    }

    public static JAspenaContext newInstance() {
        return new JAspenaContext();
    }

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
        Map<String, Method> setters = new HashMap<>();
        try {
            for (Field fld : aspFields) {
                getters.put(fld.getName(), ReflectionUtils.getGetterForField(fld));
                setters.put(fld.getName(), ReflectionUtils.getSetterForField(fld));
            }
        } catch (NoSuchMethodException ex) {
            throw new JAspenaContextException(
                    "Registration failed because no suitable setter or getter method exists. Nested exception: "
                            + ex.getMessage());
        } catch (SecurityException ex) {
            throw new JAspenaContextException("Security exception while registering predicate: " + ex.getMessage());
        }
        this.getterRegistry.put(clazz, getters);
        this.setterRegistry.put(clazz, setters);
        this.knownPredicateTypes.add(clazz);
    }

    public Set<Class<?>> getKnownPredicateTypes() {
        return Collections.unmodifiableSet(this.knownPredicateTypes);
    }

    public boolean isPredicateTypeRegistered(Class<?> clazz) {
        return this.knownPredicateTypes.contains(clazz);
    }

    /**
     * @return the defaultDecimalFormat
     */
    public DecimalFormat getDefaultDecimalFormat() {
        return this.defaultDecimalFormat;
    }

}
