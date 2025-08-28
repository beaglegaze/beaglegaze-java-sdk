package web3.beaglegaze;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark methods that require a micro-payment for each call.
 * Used together with @{@link MicroPaymentAspect} to handle the payment logic.
 * This annotation specifies the price for ONE method call.
 * 
 * @author steffenboe
 * @version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PayPerCall {

    /**
     * The price for the method call in micro units.
     */
    long price() default 0L;
}
