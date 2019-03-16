/**
 * 
 */
package io.github.madmike200590.jaspena.core;

import org.junit.Test;

/**
 * @author Michael Langowski, e1426581@student.tuwien.ac.at
 *
 */
public class AspObjectMapperFactoryTest {

    @Test
    public void smokeTest() {
        AspObjectMapperFactory factory = new AspObjectMapperFactory();
        factory.createObjectMapper(Truck.class);
    }

}
