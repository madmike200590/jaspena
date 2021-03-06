/**
 * 
 */
package io.github.madmike200590.jaspena.core.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.madmike200590.jaspena.annotations.AspField;

/**
 * @author Michael Langowski, e1426581@student.tuwien.ac.at
 *
 */
// TODO take into account boolean fields with "isFLDNAME" naming
public final class ReflectionUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtils.class);

    private ReflectionUtils() {

    }

    public static List<Field> getAspFieldsForType(Class<?> clazz) {
        return ReflectionUtils.getFieldsAnnotatedWith(clazz, AspField.class);
    }

    public static List<Field> getFieldsAnnotatedWith(Class<?> clazz, Class<? extends Annotation> annotation) {
        List<Field> fields = new ArrayList<>();
        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        Class<?> tmpSuperClass = clazz;
        while ((tmpSuperClass = tmpSuperClass.getSuperclass()) != null) {
            fields.addAll(Arrays.asList(tmpSuperClass.getDeclaredFields()));
        }
        return fields.stream().filter(f -> f.isAnnotationPresent(annotation)).collect(Collectors.toList());
    }

    public static Method getGetterForField(Field fld) throws NoSuchMethodException, SecurityException {
        Class<?> clazz = fld.getDeclaringClass();
        Method candidate = clazz.getMethod(ReflectionUtils.buildGetterNameFor(fld.getName()));
        if (ReflectionUtils.isGetterFor(fld, candidate)) {
            return candidate;
        } else {
            throw new NoSuchMethodException("No suitable getter method for field " + fld.getName());
        }
    }

    private static boolean isGetterFor(Field field, Method method) {
        Class<?> fldType = field.getType();
        Class<?> returnType = method.getReturnType();
        if (!field.getDeclaringClass().equals(method.getDeclaringClass())) {
            LOGGER.warn("Something seems severly wrong - field and checked method are of different class");
            return false;
        }
        if (!Modifier.isPublic(method.getModifiers())) {
            return false;
        }
        if (!fldType.equals(returnType)) {
            return false;
        }
        String expectedGetterName = ReflectionUtils.buildGetterNameFor(field.getName());
        return method.getName().equals(expectedGetterName);
    }

    private static String buildGetterNameFor(String fieldName) {
        return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    public static Method getSetterForField(Field fld) throws NoSuchMethodException, SecurityException {
        Class<?> clazz = fld.getDeclaringClass();
        Method candidate = clazz.getMethod(ReflectionUtils.buildSetterName(fld.getName()), fld.getType());
        if (ReflectionUtils.isSetterFor(fld, candidate)) {
            return candidate;
        } else {
            throw new NoSuchMethodException("No suitable setter method for field " + fld.getName());
        }
    }

    private static boolean isSetterFor(Field field, Method method) {
        Class<?> fldType = field.getType();
        int setterParamCount = method.getParameterCount();
        if (setterParamCount != 1) {
            return false;
        }
        Class<?> paramType = method.getParameterTypes()[0];
        if (!fldType.equals(paramType)) {
            return false;
        }
        Class<?> returnType = method.getReturnType();
        if (!returnType.equals(Void.TYPE)) {
            return false;
        }
        String expectedSetterName = ReflectionUtils.buildSetterName(field.getName());
        return method.getName().equals(expectedSetterName);
    }

    private static String buildSetterName(String fieldName) {
        return "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

}
