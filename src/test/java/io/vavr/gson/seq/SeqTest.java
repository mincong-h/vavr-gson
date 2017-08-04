package io.vavr.gson.seq;

import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import io.vavr.collection.Seq;
import io.vavr.gson.AbstractTest;
import org.junit.Test;

import java.lang.reflect.Type;

public abstract class SeqTest<T extends Seq<?>> extends AbstractTest {

    abstract T of(Object... arr);
    abstract Class<?> clz();
    abstract Type type();
    abstract Type typeWithNestedType();

    @Test(expected = JsonParseException.class)
    public void badJson() {
        gson.fromJson("1", type());
    }

    @Test
    public void serialize() {
        assert gson.toJson(of(1, 2)).equals("[1,2]");
    }

    @Test
    public void deserializeSimpleType() {
        Object obj = gson.fromJson("[1,2]", clz());
        assert clz().isAssignableFrom(obj.getClass());
        Seq<?> seq = (Seq<?>) obj;
        assert seq.head() instanceof JsonPrimitive;
        assert seq.map(e -> ((JsonPrimitive) e).getAsInt()).equals(of(1, 2));
    }

    @Test
    public void deserialize() {
        Seq<Integer> seq = gson.fromJson("[1,2]", type());
        assert clz().isAssignableFrom(seq.getClass());
        assert seq.head().getClass() == Integer.class;
        assert seq.equals(of(1, 2));
    }

    @Test
    public void deserializeWithCast() {
        Seq<Integer> seq = gson.fromJson("[\"1\",\"2\"]", type());
        assert clz().isAssignableFrom(seq.getClass());
        assert seq.head().getClass() == Integer.class;
        assert seq.equals(of(1, 2));
    }

    @Test
    public void deserializeNested() {
        Seq<Seq<Integer>> seq = gson.fromJson("[[1],[2]]", typeWithNestedType());
        assert clz().isAssignableFrom(seq.head().getClass());
        assert seq.equals(of(of(1), of(2)));
    }
}
