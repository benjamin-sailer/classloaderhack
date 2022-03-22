# classloader hack example

This project is a snipplet intended to see how a multi-module-project can use several incompatible versions of a
dependency without runtime exceptions.  The basic idea is to define a custom classloader that inverts the
class loading hierarchy (local first instead of parent first)  and use it for loading the entry classes of the
"deprecated-dependency" module.

The example uses two versions of jackson that have mutually exclusive methods.

Unfortunately, up to now the URLClassLoader is not able to load classes from jars in jars, so I have to fix that
before running the result would also work with the packaged (shadowed) jar.

## build

```
$ mvn clean install
```

## run

```
$ java -cp app/target/app-1.0-SNAPSHOT.jar de.bsailer.classloaderhack.app.App
```
