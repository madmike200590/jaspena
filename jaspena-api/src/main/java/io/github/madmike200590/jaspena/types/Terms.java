package io.github.madmike200590.jaspena.types;

import io.github.madmike200590.jaspena.types.term.Term;
import io.github.madmike200590.jaspena.types.term.impl.ConstantTerm;
import io.github.madmike200590.jaspena.types.term.impl.VariableTerm;

public final class Terms {

    private Terms() {
    }

    public VariableTerm variable(String str) {
        return new VariableTerm(str);
    }

    public Term constant(String str) {
        return new ConstantTerm<String>(str);
    }

    public Term string(String str) {
        return null;
    }

    public Term integer(int num) {
        return null;
    }

}
