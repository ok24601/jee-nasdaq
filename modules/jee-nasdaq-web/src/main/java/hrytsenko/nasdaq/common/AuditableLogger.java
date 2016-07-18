package hrytsenko.nasdaq.common;

import java.util.logging.Logger;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import com.google.common.base.Stopwatch;

@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
@Auditable
public class AuditableLogger {

    private static final Logger LOGGER = Logger.getLogger(AuditableLogger.class.getName());

    @AroundInvoke
    public Object log(InvocationContext context) throws Exception {
        Stopwatch stopwatch = Stopwatch.createStarted();
        try {
            return context.proceed();
        } finally {
            LOGGER.info(() -> String.format("%s::%s completed in %s.", context.getTarget().getClass().getSimpleName(),
                    context.getMethod().getName(), stopwatch));
        }
    }

}
