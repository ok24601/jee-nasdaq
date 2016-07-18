package hrytsenko.nasdaq.common;

import java.lang.reflect.Member;

import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.InjectionPoint;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import hrytsenko.nasdaq.AppException;

public class PropertyConfigurerTest {

    private static final String TEST_PROPERTIES = "test.properties";

    private PropertyConfigurer propertyConfigurer;

    @Before
    public void init() {
        propertyConfigurer = new PropertyConfigurer();
        propertyConfigurer.initPropertiesFrom(TEST_PROPERTIES);
    }

    @Test(expected = AppException.class)
    public void testFileNotFound() {
        propertyConfigurer.initPropertiesFrom("unknown.properties");
    }

    @Test(expected = AppException.class)
    public void testPropertyNotFound() {
        InjectionPoint point = createInjectionPoint("unknown");
        propertyConfigurer.injectString(point);
    }

    @Test
    public void testInjectString() {
        InjectionPoint point = createInjectionPoint("value");
        String value = propertyConfigurer.injectString(point);

        Assert.assertEquals("foo", value);
    }

    @Test
    public void testInjectStrings() {
        InjectionPoint point = createInjectionPoint("values");
        String[] values = propertyConfigurer.injectStrings(point);

        Assert.assertArrayEquals(new String[] { "foo", "bar" }, values);
    }

    private InjectionPoint createInjectionPoint(String name) {
        Property property = Mockito.mock(Property.class);
        Mockito.doReturn(name).when(property).value();
        Annotated annotated = Mockito.mock(Annotated.class);
        Mockito.doReturn(property).when(annotated).getAnnotation(Property.class);

        Member member = Mockito.mock(Member.class);
        Mockito.doReturn(PropertyConfigurerTest.class).when(member).getDeclaringClass();

        InjectionPoint point = Mockito.mock(InjectionPoint.class);
        Mockito.doReturn(annotated).when(point).getAnnotated();
        Mockito.doReturn(member).when(point).getMember();
        return point;
    }

}
