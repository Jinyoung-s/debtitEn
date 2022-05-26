package so.debateit.config;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class SecurityChecker {

    private static final Log logger = LogFactory.getLog(SecurityChecker.class);

    @Pointcut("@annotation(SecurityCheck)")
    public void checkMethodSecurity(){}

    @Around("checkMethodSecurity()")
    public Object checkSecurity(ProceedingJoinPoint joihPoint) throws Throwable {
        logger.debug("Checking method security....");

        Object result = joihPoint.proceed();
        return result;
    }
}
