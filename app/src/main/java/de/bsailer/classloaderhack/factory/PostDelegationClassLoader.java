package de.bsailer.classloaderhack.factory;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

public class PostDelegationClassLoader extends URLClassLoader {

    public PostDelegationClassLoader(final List<? extends URL> urls) {
        super(urls.toArray(new URL[0]));
    }

    public Class<?> loadClass(final String name) throws ClassNotFoundException {
        final Class<?> alreadyLoadedClass = findLoadedClass(name);
        if (alreadyLoadedClass != null) {
            return alreadyLoadedClass;
        }
        return findNewClassWithPostDelegation(name);
    }

    private Class<?> findNewClassWithPostDelegation(final String name) throws ClassNotFoundException {
        try {
            final Class<?> locallyFoundClass = findClassLocally(name);
            System.out.printf("locallyFoundClass: %s%n", locallyFoundClass);
            return locallyFoundClass;
        } catch (final ClassNotFoundException e) {
            final Class<?> classFoundUpTheHierarchy = findClassViaDelegation(name);
            System.out.printf("classFoundUpTheHierarchy: %s%n", classFoundUpTheHierarchy);
            return classFoundUpTheHierarchy;
        }
    }

    public URL getResource(final String name) {
        final URL locallyFoundResource = findResource(name);
        if (locallyFoundResource != null) {
            return locallyFoundResource;
        }
        return super.findResource(name);
    }

    private Class<?> findClassViaDelegation(final String name) throws ClassNotFoundException {
        return super.loadClass(name);
    }

    private Class<?> findClassLocally(final String name) throws ClassNotFoundException {
        return findClass(name);
    }

}
