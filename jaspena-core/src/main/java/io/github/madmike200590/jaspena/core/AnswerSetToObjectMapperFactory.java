package io.github.madmike200590.jaspena.core;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.madmike200590.jaspena.annotations.Atom;
import io.github.madmike200590.jaspena.core.reflect.ReflectionUtils;
import io.github.madmike200590.jaspena.mapper.IAnswerSetToObjectMapper;

public class AnswerSetToObjectMapperFactory {

    private static final Logger              LOGGER      = LoggerFactory
            .getLogger(AnswerSetToObjectMapperFactory.class);

    private final Map<Class<?>, List<Field>> mappedAtoms = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <T> IAnswerSetToObjectMapper<T> createMapperFor(Class<T> clazz) {
        if (!this.mappedAtoms.containsKey(clazz)) {
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

    public void register(Class<?> clazz) {
        List<Field> atomProperties = ReflectionUtils.getFieldsAnnotatedWith(clazz, Atom.class);
        if (atomProperties.isEmpty()) {
            throw new IllegalArgumentException("Type " + clazz.getSimpleName() + " has no annotated fields!");
        }
        String atomName;
        for(Field fld : atomProperties) {
            
        }
        this.mappedAtoms.put(clazz, atomProperties);
    }

}
