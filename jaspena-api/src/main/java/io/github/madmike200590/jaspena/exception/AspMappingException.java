/**
 * 
 */
package io.github.madmike200590.jaspena.exception;

/**
 * @author Michael Langowski, e1426581@student.tuwien.ac.at
 *
 */
public class AspMappingException extends Exception {

    private static final long serialVersionUID = 1L;

    public AspMappingException(String message) {
        super(message);
    }

    public AspMappingException(String aspType, Class<?> javaType) {
        this("Error mapping between " + aspType + " and " + javaType.getSimpleName());
    }

}
