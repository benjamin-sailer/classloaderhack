package de.bsailer.classloaderhack.factory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ClassLoaderInjectorHandler implements InvocationHandler {

    private final Object proxyInstance;

    private final ClassLoader injectedClassLoader;

    public ClassLoaderInjectorHandler(final Object instance, final ClassLoader classLoader) {
        proxyInstance = instance;
        injectedClassLoader = classLoader;
    }

   @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        final ClassLoader originalLoader = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(injectedClassLoader);
            return method.invoke(proxyInstance, args);
        } finally {
            Thread.currentThread().setContextClassLoader(originalLoader);
        }
    }

    public static <T> T wrapClassLoaderInjector(final Object instance,
                                                final ClassLoader classLoader,
                                                final Class<T> primaryInterface) {
        final InvocationHandler handler = new ClassLoaderInjectorHandler(instance, classLoader);
        final Object proxyInstance = Proxy.newProxyInstance(classLoader, new Class<?>[]{primaryInterface}, handler);
        @SuppressWarnings("unchecked")
        final T castedProxyInstance = (T) proxyInstance;
        return castedProxyInstance;
    }

}
