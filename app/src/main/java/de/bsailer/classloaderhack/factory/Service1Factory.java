package de.bsailer.classloaderhack.factory;

import de.bsailer.classloaderhack.api.service.Service1;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Service1Factory {

    public static Service1 create() {
        final List<String> jars = Arrays.asList(
                "/lib/service1.jar",
                "/lib/jackson-annotations.jar",
                "/lib/jackson-core.jar",
                "/lib/jackson-databind.jar"
        );
        final List<URL> urls = jars.stream().map(Service1Factory.class::getResource).collect(Collectors.toList());
        System.out.printf("URLs created: %s%n", urls);
        final URLClassLoader loader = new PostDelegationClassLoader(urls);
        return newInstance(loader,
                "de.bsailer.classloaderhack.service1.OldFrameworkUsingServiceImpl",
                new Class<?>[0],
                new Object[0],
                Service1.class);
    }

    public static <T> T newInstance(final ClassLoader classLoader,
                                    final String className,
                                    final Class<?>[] formalParameters,
                                    final Object[] constructorArgs,
                                    final Class<T> primaryInterface) {
        try {
            final Class<?> theClass = classLoader.loadClass(className);
            final Object instance = theClass
                    .getDeclaredConstructor(formalParameters)
                    .newInstance(constructorArgs);
            return ClassLoaderInjectorHandler.wrapClassLoaderInjector(instance,
                    classLoader,
                    primaryInterface);
        } catch (ClassNotFoundException
                | NoSuchMethodException
                | IllegalAccessException
                | InvocationTargetException
                | InstantiationException e) {
            throw new IllegalStateException(e);
        }
    }

}
