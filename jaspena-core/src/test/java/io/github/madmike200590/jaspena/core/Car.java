/**
 * 
 */
package io.github.madmike200590.jaspena.core;

import io.github.madmike200590.jaspena.annotations.AspField;
import io.github.madmike200590.jaspena.annotations.AspId;
import io.github.madmike200590.jaspena.annotations.AspPredicate;

/**
 * @author Michael Langowski, e1426581@student.tuwien.ac.at
 *
 */
@AspPredicate(name = "vehicle")
public class Car {

    @AspField(name = "manufacturer")
    private String brand;

    @AspField
    private String model;

    @AspField
    @AspId
    private String fin;

    @AspField
    private int    year;

    @AspField
    private double hp;

    /**
     * @return the brand
     */
    public String getBrand() {
        return this.brand;
    }

    /**
     * @param brand
     *            the brand to set
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * @return the model
     */
    public String getModel() {
        return this.model;
    }

    /**
     * @param model
     *            the model to set
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * @return the fin
     */
    public String getFin() {
        return this.fin;
    }

    /**
     * @param fin
     *            the fin to set
     */
    public void setFin(String fin) {
        this.fin = fin;
    }

    /**
     * @return the year
     */
    public int getYear() {
        return this.year;
    }

    /**
     * @param year
     *            the year to set
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * @return the hp
     */
    public double getHp() {
        return this.hp;
    }

    /**
     * @param hp
     *            the hp to set
     */
    public void setHp(double hp) {
        this.hp = hp;
    }

}
