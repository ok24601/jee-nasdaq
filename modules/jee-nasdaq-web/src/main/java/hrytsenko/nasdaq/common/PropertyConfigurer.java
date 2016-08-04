package hrytsenko.nasdaq.common;

import java.io.ByteArrayInputStream;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import com.google.common.collect.Maps;
import com.google.common.io.Resources;

import hrytsenko.nasdaq.AppException;

@ApplicationScoped
public class PropertyConfigurer {

    private static final Logger LOGGER = Logger.getLogger(PropertyConfigurer.class.getName());

    private static final String APP_PROPERTIES = "app.properties";

    private Map<String, String> properties;

    private static Map<String, String> loadProperties(String filename) {
        LOGGER.info(() -> String.format("Load properties from file %s.", filename));

        try {
            byte[] content = Resources.toByteArray(Resources.getResource(filename));
            Properties properties = new Properties();
            properties.load(new ByteArrayInputStream(content));
            return Maps.fromProperties(properties);
        } catch (Exception exception) {
            throw new AppException("Could not load properties.", exception);
        }
    }

    @PostConstruct
    public void initProperties() {
        initPropertiesFrom(APP_PROPERTIES);
    }

    public void initPropertiesFrom(String filename) {
        this.properties = Collections.unmodifiableMap(loadProperties(filename));
    }

    @Produces
    @Dependent
    @Property
    public String injectString(InjectionPoint point) {
        String name = point.getAnnotated().getAnnotation(Property.class).value();
        LOGGER.info(() -> String.format("Inject property %s into %s.", name,
                point.getMember().getDeclaringClass().getName()));

        if (!properties.containsKey(name)) {
            throw new AppException(String.format("Property %s not found.", name));
        }
        return properties.get(name);
    }

    @Produces
    @Dependent
    @Property
    public String[] injectStrings(InjectionPoint point) {
        return injectString(point).split(",");
    }

}
