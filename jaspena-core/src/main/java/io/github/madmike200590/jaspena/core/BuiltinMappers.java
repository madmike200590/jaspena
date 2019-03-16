/**
 * 
 */
package io.github.madmike200590.jaspena.core;

/**
 * @author Michael Langowski, e1426581@student.tuwien.ac.at
 *
 */
public final class BuiltinMappers {

    private BuiltinMappers() {
    }

    public String mapStringValueToAsp(String val) {
        return "\"" + val + "\"";
    }

    public String mapIntegerValueToAsp(int val) {
        return "-1";
    }

    public String mapBooleanValueToAsp(boolean val) {
        return "false";
    }

}
