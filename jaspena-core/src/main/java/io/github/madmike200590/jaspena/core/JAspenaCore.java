/**
 * 
 */
package io.github.madmike200590.jaspena.core;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import io.github.madmike200590.jaspena.annotations.AspPredicate;
import io.github.madmike200590.jaspena.core.reflect.ReflectionUtils;

/**
 * @author Michael Langowski, e1426581@student.tuwien.ac.at
 *
 */
public class JAspenaCore {

    public static String writeAspPredicate(Object o) throws SecurityException, ReflectiveOperationException {
        if (!o.getClass().isAnnotationPresent(AspPredicate.class)) {
            throw new RuntimeException("Not an ASP predicate!");
        }
        return JAspenaCore.writeAspPredicate(o.getClass().getSimpleName(),
                ReflectionUtils.getAspFieldsForType(o.getClass()), o);
    }

    public static String writeAspPredicate(String name, List<Field> fields, Object o)
            throws ReflectiveOperationException, SecurityException {
        Method getter;
        StringBuilder bld = new StringBuilder(name);
        bld.append("(");
        List<String> aspFields = new ArrayList<>();
        for (Field fld : fields) {
            getter = ReflectionUtils.getGetterForField(fld);
            aspFields.add(getter.invoke(o).toString());
        }
        bld.append(StringUtils.join(aspFields, ", "));
        bld.append(")");
        return bld.toString();
    }
}
