package org.do6po.ioc;

import lombok.Getter;

import java.lang.reflect.Constructor;
import java.util.LinkedHashSet;
import java.util.Set;

public class ClassMetadata <T> {
    @Getter
    private final Class<T> tClass;
    @Getter
    private final Constructor<T> constructor;
    @Getter
    private final Set<Class<?>> dependencies = new LinkedHashSet<>();

    public ClassMetadata(Class<T> tClass, Constructor<T> constructor) {
        this.tClass = tClass;
        this.constructor = constructor;
    }
}
