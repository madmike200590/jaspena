package io.github.madmike200590.jaspena.types;

import org.junit.Assert;
import org.junit.Test;

public class AspStringTest {

    @Test
    public void testValueOf1() {
        String bla = "bla";
        AspString aspBla = AspString.valueOf(bla);
        Assert.assertEquals("\"" + bla + "\"", aspBla.getValue());
        Assert.assertEquals(bla, aspBla.stringValue());
        Assert.assertEquals(true, Formats.matchesStringFormat(aspBla.getValue()));
    }

    @Test
    public void testValueOf2() {
        String bla = "bla bla bla";
        AspString aspBla = AspString.valueOf(bla);
        Assert.assertEquals("\"" + bla + "\"", aspBla.getValue());
        Assert.assertEquals(bla, aspBla.stringValue());
        Assert.assertEquals(true, Formats.matchesStringFormat(aspBla.getValue()));
    }

    @Test
    public void testValueOfStringWithQuotes() {
        String bla = "bla \"bla quoted \"bla\"";
        AspString aspBla = AspString.valueOf(bla);
        Assert.assertEquals("\"bla \\\\\"bla quoted \\\\\"bla\\\\\"\"", aspBla.getValue());
        Assert.assertEquals(bla, aspBla.stringValue());
        Assert.assertEquals(true, Formats.matchesStringFormat(aspBla.getValue()));
    }

}
