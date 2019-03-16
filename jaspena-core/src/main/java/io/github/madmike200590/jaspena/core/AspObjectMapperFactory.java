/**
 * 
 */
package io.github.madmike200590.jaspena.core;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.madmike200590.jaspena.mapper.AspObjectMapper;

/**
 * @author Michael Langowski, e1426581@student.tuwien.ac.at
 *
 */
public class AspObjectMapperFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(AspObjectMapperFactory.class);

    public <T> AspObjectMapper<T> createObjectMapper(Class<T> clazz) {
        List<Field> fields = new ArrayList<>();
        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        Class<?> tmpSuperClass = clazz;
        while ((tmpSuperClass = tmpSuperClass.getSuperclass()) != null) {
            fields.addAll(Arrays.asList(tmpSuperClass.getDeclaredFields()));
        }
        for (Field fld : fields) {
            LOGGER.debug("Class {}, field{ {} {} {} }", clazz.getSimpleName(), Modifier.toString(fld.getModifiers()),
                    fld.getType().getSimpleName(), fld.getName());
        }
        return null;
    }

}
