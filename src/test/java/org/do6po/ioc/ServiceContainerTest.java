package org.do6po.ioc;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ServiceContainerTest {

    ServiceContainer serviceContainer = new ServiceContainer();

    @Test
    void createInstanceOfSimpleClass_WithoutConstruct() {
        var instance = serviceContainer.make(SomeClassWithoutConstruct.class);
        assertThat(instance)
                .isInstanceOf(SomeClassWithoutConstruct.class);
    }

    @Test
    void createInstanceOfSimpleClass_WithConstruct() {
        var instance = serviceContainer.make(SomeClassWithConstruct.class);
        assertThat(instance)
                .isInstanceOf(SomeClassWithConstruct.class);
    }

    @Test
    void createInstanceOfSimpleClass_WithConstructAndOneDependency() {
        var instance = serviceContainer.make(SomeClassWithConstructAndOneDependency.class);
        assertThat(instance)
                .isInstanceOf(SomeClassWithConstructAndOneDependency.class);
    }

    static class SomeClassWithoutConstruct {}

    @NoArgsConstructor
    static class SomeClassWithConstruct {}

    @RequiredArgsConstructor
    static class SomeClassWithConstructAndOneDependency {
        private final SomeClassWithoutConstruct someClassWithoutConstruct;
    }
}
