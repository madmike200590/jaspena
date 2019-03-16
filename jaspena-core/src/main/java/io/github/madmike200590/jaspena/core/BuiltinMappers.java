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

    public String mapStringValue(String val) {
        return "";
    }

    public String mapIntegerValue(int val) {
        return "-1";
    }

    public String mapBoolean(boolean val) {
        return "false";
    }

}
