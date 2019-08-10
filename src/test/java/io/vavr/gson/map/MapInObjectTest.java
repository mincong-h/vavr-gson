package io.vavr.gson.map;

import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import io.vavr.gson.AbstractTest;
import org.junit.Test;

public class MapInObjectTest extends AbstractTest  {
    @Test
    public void testTypeHierarchyAdapters() {
        Foo foo = gson.fromJson("{\"config\":{\"k\":1}}", Foo.class);
        assert foo.config.equals(HashMap.of("k", 1));
        assert gson.toJson(foo).equals("{\"config\":{\"k\":1}}");
    }

    private static class Foo {
        private Map<String, Integer> config;

        Foo() {
            // no-args constructor
        }
    }
}
