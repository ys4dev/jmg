package sample.web;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.util.function.Consumer;

/**
 * Created by sakura on 2016/10/30.
 */
class Capturing<T> extends BaseMatcher<T> {

    static <T> Capturing<T> capturing(Matcher<T> matcher, Consumer<T> c) {
        return new Capturing<T>(matcher, c);
    }

    private final Matcher<T> matcher;
    private final Consumer<T> consumer;

    private Capturing(Matcher<T> matcher, Consumer<T> consumer) {
        this.matcher = matcher;
        this.consumer = consumer;
    }

    @Override
    public boolean matches(Object item) {
        try {
            @SuppressWarnings("unchecked")
            T t = (T) item;
            consumer.accept(t);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return matcher.matches(item);
    }

    @Override
    public void describeTo(Description description) {
        matcher.describeTo(description);
    }
}
