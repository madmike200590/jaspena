package io.github.madmike200590.jaspena.types;

public class AspString {

    private String value;

    private AspString(String val) {
        this.value = val;
    }

    public static AspString valueOf(String string) {
        return new AspString("\"" + string.replace("\"", "\\\\\"") + "\"");
    }

    public String stringValue() {
        String unquoted = this.value.substring(1, this.value.length() - 1);
        return unquoted.replace("\\\\\"", "\"");
    }

    public boolean equals(Object o) {
        if (!(o instanceof AspString)) {
            return false;
        }
        AspString other = (AspString) o;
        return this.value.equals(other.value);
    }

    public int hashCode() {
        return this.value.hashCode();
    }

    public String toString() {
        return this.stringValue();
    }

    public String getValue() {
        return this.value;
    }

}
