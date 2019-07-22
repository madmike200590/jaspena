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

    private static final Logger                                        LOGGER                      = LoggerFactory
            .getLogger(JAspenaContext.class);

    // key is input type
    private final Map<Class<?>, Function<Object, String>>              defaultOutboundMappers;
    private final Map<Class<?>, Function<Object, String>>              outboundMappers             = Collections
            .synchronizedMap(new HashMap<>());

    private final Set<Class<?>>                                        knownPredicateTypes         = Collections
            .synchronizedSet(new HashSet<>());
    private final Map<Class<?>, Map<String, Method>>                   getterRegistry              = Collections
            .synchronizedMap(new HashMap<>());
    private final Map<Class<?>, Map<String, Method>>                   setterRegistry              = Collections
            .synchronizedMap(new HashMap<>());
    private final Map<Class<?>, Map<String, Function<Object, String>>> outboundFieldMapperRegistry = Collections
            .synchronizedMap(new HashMap<>());

    private JAspenaContext() {
        Map<Class<?>, Function<Object, String>> fieldToStringMappers = new HashMap<>();
        fieldToStringMappers.put(String.class, BuiltinMappers::mapStringValueToAsp);
        fieldToStringMappers.put(Boolean.class, BuiltinMappers::mapBooleanValueToAsp);
        fieldToStringMappers.put(Byte.class, BuiltinMappers::mapByteValueToAsp);
        fieldToStringMappers.put(Character.class, BuiltinMappers::mapCharacterValueToAsp);
        fieldToStringMappers.put(Short.class, BuiltinMappers::mapShortValueToAsp);
        fieldToStringMappers.put(Integer.class, BuiltinMappers::mapIntegerValueToAsp);
        fieldToStringMappers.put(Long.class, BuiltinMappers::mapLongValueToAsp);
        fieldToStringMappers.put(Float.class, BuiltinMappers::mapFloatValueToAsp);
        fieldToStringMappers.put(Double.class, BuiltinMappers::mapDoubleValueToAsp);
        this.defaultOutboundMappers = Collections.unmodifiableMap(fieldToStringMappers);

        // this.defaultStringToFieldMappers = new HashMap<>();
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

    private Function<Object, String> getOutboundMapperForField(Field fld) {

        return null;
    }

    public Set<Class<?>> getKnownPredicateTypes() {
        return Collections.unmodifiableSet(this.knownPredicateTypes);
    }

    public boolean isPredicateTypeRegistered(Class<?> clazz) {
        return this.knownPredicateTypes.contains(clazz);
    }

}
