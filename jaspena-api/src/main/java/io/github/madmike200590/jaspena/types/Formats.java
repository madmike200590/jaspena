package io.github.madmike200590.jaspena.types;

import java.util.regex.Pattern;

public final class Formats {

    public static final Pattern VARNAME_FMT    = Pattern.compile("(\\_)|([A-Z][a-zA-Z0-9\\_]*)");
    public static final Pattern CONSTNAME_FMT  = Pattern.compile("[a-z][a-zA-Z0-9\\_]*");
    public static final Pattern STRING_FMT     = Pattern.compile("\"((\\\\\")|[^\"])*\"");
    public static final Pattern IDENTIFIER_FMT = Formats.CONSTNAME_FMT;

    private Formats() {
    }

    public static boolean matchesVariableFormat(String varname) {
        return Formats.matches(varname, Formats.VARNAME_FMT);
    }

    public static boolean matchesConstantFormat(String constSym) {
        return Formats.matches(constSym, Formats.CONSTNAME_FMT);
    }

    public static boolean matchesStringFormat(String string) {
        return Formats.matches(string, Formats.STRING_FMT);
    }

    public static boolean matchesIdentifierFormat(String id) {
        return Formats.matches(id, Formats.IDENTIFIER_FMT);
    }

    private static boolean matches(String str, Pattern p) {
        return p.matcher(str).matches();
    }

}
