/**
 * 
 */
package io.github.madmike200590.jaspena.core;

/**
 * @author Michael Langowski, e1426581@student.tuwien.ac.at
 *
 */
public class Truck extends Car {

    private double  wheels;
    private boolean hasLoadingBed;

    /**
     * @return the wheels
     */
    public double getWheels() {
        return this.wheels;
    }

    /**
     * @param wheels
     *            the wheels to set
     */
    public void setWheels(double wheels) {
        this.wheels = wheels;
    }

    /**
     * @return the hasLoadingBed
     */
    public boolean isHasLoadingBed() {
        return this.hasLoadingBed;
    }

    /**
     * @param hasLoadingBed
     *            the hasLoadingBed to set
     */
    public void setHasLoadingBed(boolean hasLoadingBed) {
        this.hasLoadingBed = hasLoadingBed;
    }

}
