/**
 * 
 */
package io.github.madmike200590.jaspena.core;

import java.math.BigDecimal;

/**
 * @author Michael Langowski, e1426581@student.tuwien.ac.at
 *
 */
public final class BuiltinMappers {

    private BuiltinMappers() {
    }

    public static String mapStringValueToAsp(Object val) {
        return (String) val;
    }

    public static String mapByteValueToAsp(Object val) {
        return Byte.toString((Byte) val);
    }

    public static String mapShortValueToAsp(Object val) {
        return Short.toString((Short) val);
    }

    public static String mapCharacterValueToAsp(Object val) {
        return BuiltinMappers.mapStringValueToAsp(new String(Character.toString((Character) val)));
    }

    public static String mapIntegerValueToAsp(Object val) {
        return Integer.toString((Integer) val);
    }

    public static String mapLongValueToAsp(Object val) {
        return Long.toString((Long) val);
    }

    public static String mapBooleanValueToAsp(Object val) {
        return Boolean.toString((Boolean) val);
    }

    public static String mapFloatValueToAsp(Object val) {
        // avoid implicit cast to double!
        return BuiltinMappers.mapBigDecimalValueToAsp(new BigDecimal(Float.toString((Float) val)));
    }

    public static String mapDoubleValueToAsp(Object val) {
        return BuiltinMappers.mapBigDecimalValueToAsp(new BigDecimal((Double) val));
    }

    public static String mapBigDecimalValueToAsp(BigDecimal val) {
        return val.toPlainString();
    }

}
