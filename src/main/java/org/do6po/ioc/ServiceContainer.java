package org.do6po.ioc;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static java.util.Objects.isNull;

public class ServiceContainer {
    private final Map<Class<?>, ClassMetadata<?>> metadataMap = new HashMap<>();

    public <T> T make(Class<T> tClass) {
        ClassMetadata<?> classMetadata = metadataMap.get(tClass);
        if (isNull(classMetadata)) {
            classMetadata = readMetadata(tClass);
            metadataMap.put(tClass, classMetadata);
        }

        return makeByMetadata(classMetadata);
    }

    private <T> ClassMetadata<?> readMetadata(Class<T> tClass) {
        Constructor<T> declaredConstructor = (Constructor<T>) tClass.getDeclaredConstructors()[0];
        ClassMetadata<T> tClassMetadata = new ClassMetadata<>(tClass, declaredConstructor);

        for (Parameter parameter : declaredConstructor.getParameters()) {
            tClassMetadata.getDependencies().add(parameter.getType());
        }

        return tClassMetadata;
    }

    private <T> T makeByMetadata(ClassMetadata<?> classMetadata) {
        Set<Class<?>> dependencies = classMetadata.getDependencies();

        if (dependencies.isEmpty()) {
            return makeInstance(classMetadata);
        }

        ArrayList<Object> parameters = new ArrayList<>(dependencies.size());

        dependencies.forEach(d -> parameters.add(make(d)));

        return makeInstance(classMetadata, parameters.toArray());
    }

    private <T> T makeInstance(ClassMetadata<?> classMetadata, Object... parameters) {
        try {
            return (T) classMetadata.getConstructor().newInstance(parameters);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
//        catch (NoSuchMethodException e) {
//            throw new RuntimeException(e);
//        }
    }
}
