/**
 * 
 */
package io.github.madmike200590.jaspena.core;

import org.junit.Assert;
import org.junit.Test;

import io.github.madmike200590.jaspena.core.exception.JAspenaContextException;

/**
 * @author Michael Langowski, e1426581@student.tuwien.ac.at
 *
 */
public class JAspenaContextTest {

    @Test
    public void registeredPredicatesSimpleTest() throws JAspenaContextException {
        JAspenaContext ctx = JAspenaContext.newInstance();
        ctx.registerPredicateType(Car.class);
        Assert.assertEquals(true, ctx.getKnownPredicateTypes().contains(Car.class));
    }

    @Test
    public void defaultDecimalFormatTest() {
        JAspenaContext ctx = JAspenaContext.newInstance();
        String s1 = ctx.getDefaultDecimalFormat().format(12345.678901);
        Assert.assertEquals("12345.6789", s1);
        String s2 = ctx.getDefaultDecimalFormat().format(0.0);
        Assert.assertEquals("0", s2);
    }

}
