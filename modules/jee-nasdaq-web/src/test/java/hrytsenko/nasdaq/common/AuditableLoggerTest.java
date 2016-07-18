package hrytsenko.nasdaq.common;

import javax.interceptor.InvocationContext;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import hrytsenko.nasdaq.AppException;

public class AuditableLoggerTest {

    @Test
    public void testLog() throws Exception {
        AuditableLogger logger = new AuditableLogger();

        InvocationContext context = createContext("testLog");
        Mockito.doReturn("foo").when(context).proceed();

        Object value = logger.log(context);

        Assert.assertEquals("foo", value);
    }

    @Test(expected = AppException.class)
    public void testException() throws Exception {
        AuditableLogger logger = new AuditableLogger();

        InvocationContext context = createContext("testException");
        Mockito.doThrow(new AppException("Unknown error.")).when(context).proceed();

        logger.log(context);
    }

    private InvocationContext createContext(String methodName) throws Exception {
        InvocationContext context = Mockito.mock(InvocationContext.class);
        Mockito.doReturn(AuditableLoggerTest.class.getMethod(methodName)).when(context).getMethod();
        Mockito.doReturn(AuditableLoggerTest.class).when(context).getTarget();
        return context;
    }

}
