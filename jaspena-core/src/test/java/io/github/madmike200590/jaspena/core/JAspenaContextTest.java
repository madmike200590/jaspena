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
        JAspenaContext ctx = new JAspenaContext();
        ctx.registerPredicateType(Car.class);
        Assert.assertEquals(true, ctx.getKnownPredicateTypes().contains(Car.class));
    }

}
