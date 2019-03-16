/**
 * 
 */
package io.github.madmike200590.jaspena.core;

import org.junit.Test;

/**
 * @author Michael Langowski, e1426581@student.tuwien.ac.at
 *
 */
public class JAspenaCoreTest {

    @Test
    public void smokeTest() throws Exception {
        Car car = new Car();
        car.setBrand("BMW");
        car.setModel("5.30ix");
        car.setFin("BMW12345");
        car.setYear(2015);
        car.setHp(100);
        String aspPred = JAspenaCore.writeAspPredicate(car);
        System.out.println("AspPred = " + aspPred);
    }

}
