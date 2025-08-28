package web3.beaglegaze;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class MicroPaymentAspect {

    private static AsyncBatchProcessor asyncBatchProcessor;

    public static void setProcessor(AsyncBatchProcessor asyncBatchProcessor) {
        MicroPaymentAspect.asyncBatchProcessor = asyncBatchProcessor;
    }

    public static void addEventObserver(MeteringEventObserver observer) {
        MicroPaymentAspect.asyncBatchProcessor.addObserver(observer);
    }

    @Around("@annotation(payPerCall) && execution(* *(..))")
    public Object logTrackedMethod(ProceedingJoinPoint joinPoint, PayPerCall payPerCall) throws Throwable {
        asyncBatchProcessor.registerCallAsync(payPerCall.price());
        if(asyncBatchProcessor.isInErrorState()) {
            throw new RuntimeException("Micro-payment processing is in error state, method execution blocked.");
        }
        return joinPoint.proceed();
    }
}
