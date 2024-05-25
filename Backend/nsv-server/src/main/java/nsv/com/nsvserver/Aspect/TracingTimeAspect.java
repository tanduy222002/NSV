package nsv.com.nsvserver.Aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TracingTimeAspect {
    @Around("@annotation(nsv.com.nsvserver.Annotation.TraceTime)")
    public Object TraceExecutionTimeOnMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        long interTime=System.currentTimeMillis();
        Object result = null;
        try{
            result = joinPoint.proceed();
        } catch( Throwable throwable){
            throwable.printStackTrace();
            throw throwable;
        }
        long outterTime=System.currentTimeMillis();
        long excetuionTime = outterTime-interTime;
        System.out.println("Method " + joinPoint.getSignature().toShortString() + "took " + excetuionTime +" ms "+"to execute");
        return result;
    }
}
