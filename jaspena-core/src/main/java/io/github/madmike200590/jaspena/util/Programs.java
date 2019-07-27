package io.github.madmike200590.jaspena.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public final class Programs {

    private Programs() {

    }

    public static String loadAspCode(Path src) throws IOException {
        return Programs.loadAspCode(src, new String[] {});
    }

    public static String loadAspCode(Path src, String... directives) throws IOException {
        StringBuilder bld = new StringBuilder();
        for (String directive : directives) {
            bld.append(directive).append(System.lineSeparator());
        }
        List<String> aspLines;
        aspLines = Files.readAllLines(src);
        aspLines.forEach((line) -> bld.append(line).append("\n"));
        return bld.toString();
    }

}
