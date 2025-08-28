package web3.beaglegaze;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class UsageLogger {
    
    @Around("@annotation(web3.beaglegaze.PayPerCall) && execution(* *(..))")
    public Object logTrackedMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getDeclaringTypeName() + "." + signature.getName();
        System.out.println("[Call tracked]: " + methodName);
        return joinPoint.proceed();
    }
}
