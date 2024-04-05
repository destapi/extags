package works.hop.eztag.parser;

import works.hop.eztag.pubsub.JEvent;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

public interface JNode extends Serializable {

    default boolean isArray() {
        return false;
    }

    default boolean isObject() {
        return false;
    }

    default JObserver observer() {
        return null;
    }

    default void observer(JObserver observer) {

    }

    default JNode parent() {
        return null;
    }

    default void parent(JNode parent) {

    }

    String path();

    void path(String path);

    default JNode root() {
        JNode prev = this;
        while (prev.parent() != null) {
            prev = prev.parent();
        }
        return prev;
    }

    default void bubble(JEvent event, Object oldValue, Object newValue) {
        if (event.getSource() == null) event.setSource(this);

        if (parent() != null) {
            parent().bubble(event, oldValue, newValue);
        }
        // this must be the root, so the observer should be available
        if (observer() != null) {
            observer().receiver().onEvent(event, oldValue, newValue);
        }
    }

    default String tracePath() {
        String fullPath = Objects.requireNonNullElse(this.path(), "");
        JNode node = this.parent();
        if (node != null) {
            do {
                fullPath = String.format("%s%s", Objects.requireNonNullElse(node.path(), ""), fullPath);
                node = node.parent();
            } while (node != null);
        }
        return fullPath;
    }

    /**
     * JArray retrieve from an array the first object that matches the predicate
     *
     * @param predicate test for presence or existence
     * @return value if found else null
     */
    default Object getItem(Predicate<Object> predicate) {
        return null;
    }

    /**
     * JArray replace first value in the array which matches predicate
     *
     * @param predicate test for presence or existence
     * @param value     value replaced in the array
     */
    default void update(Predicate<Object> predicate, Object value) {

    }

    /**
     * JArray update an object in the collection in-place
     *
     * @param predicate test for presence or existence
     * @param consumer  function to update an object in-place
     */
    default void update(Predicate<Object> predicate, Consumer<JNode> consumer) {

    }

    /**
     * JObject update an object in the map in-place
     *
     * @param key      value used as a key in the map
     * @param consumer function to update an object in-place
     */
    default void update(String key, Consumer<JNode> consumer) {

    }

    /**
     * JArray remove from an array the first object that matches the predicate
     *
     * @param predicate test for presence or existence
     * @return value if found else null
     */
    default Object removeFirst(Predicate<Object> predicate) {
        return null;
    }

    /**
     * JArray remove from an array any object that matches the predicate
     *
     * @param predicate test for presence or existence
     */
    default void removeAny(Predicate<Object> predicate) {

    }

    int size();

    boolean isEmpty();

    void clear();
}
