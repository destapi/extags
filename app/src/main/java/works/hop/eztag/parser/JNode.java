package works.hop.eztag.parser;

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

    default JNode root() {
        JNode prev = this;
        while (prev.parent() != null) {
            prev = prev.parent();
        }
        return prev;
    }

    default void bubble(JEvent event, JNode data) {
        if (event.source == null) event.source = this;

        if (parent() != null) {
            parent().bubble(event, data);
        }
        // this must be the root, so the observer should be available
        if (observer() != null) {
            observer().receiver().onEvent(event, data);
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
     * JObject retrieve from a map the value reachable by key
     *
     * @param subscriber key to register interest for notification if the value changes
     * @param key        value used as a key in the map
     * @return value if key exists else null
     */
    default Object getItem(String subscriber, String key) {
        return null;
    }

    /**
     * JArray retrieve from the array the value at index
     *
     * @param subscriber key to register interest for notification if the value changes
     * @param index      ordinal position of an element in array
     * @return value at the index position if it's within valid range
     */
    default Object getIndexed(String subscriber, int index) {
        return null;
    }

    /**
     * JArray retrieve from an array the first object that matches the predicate
     *
     * @param subscriber key to register interest for notification if the value changes
     * @param predicate  test for presence or existence
     * @return value if found else null
     */
    default Object getItem(String subscriber, Predicate<Object> predicate) {
        return null;
    }

    /**
     * JArray replace in an array the value at index
     *
     * @param index ordinal position of an element to replace in the array
     * @param value new value intended
     * @return old value at the index position if it's within valid range
     */
    default Object replaceIndexed(int index, Object value) {
        return null;
    }

    /**
     * JArray replace first value in the array which matches predicate
     *
     * @param predicate test for presence or existence
     * @param value     value replaced in the array
     */
    default void replaceItem(Predicate<Object> predicate, Object value) {

    }

    /**
     * JObject add new value with given key into a map
     *
     * @param key   value used as a key in the map
     * @param value value put into the map
     */
    default void putItem(String key, Object value) {

    }

    /**
     * JObject replace value with a given key in a map
     *
     * @param key   value used as a key in the map
     * @param value value replaced in the map
     */
    default void replaceItem(String key, Object value) {

    }

    /**
     * JArray update an object in the collection in-place
     *
     * @param predicate test for presence or existence
     * @param consumer  function to update an object in-place
     */
    default void updateItem(Predicate<Object> predicate, Consumer<JNode> consumer) {

    }

    /**
     * JObject update an object in the map in-place
     *
     * @param parent   value of parent node if it exists
     * @param key      value used as a key in the map
     * @param consumer function to update an object in-place
     */
    default void update(JNode parent, String key, Consumer<JNode> consumer) {

    }

    /**
     * JArray add an element to the array
     *
     * @param value value added to the array
     */
    default boolean addItem(Object value) {
        return false;
    }

    /**
     * JArray remove from the array the value at index
     *
     * @param index ordinal position of an element in array
     * @return value at the index position if it's within valid range
     */
    default Object removeIndexed(int index) {
        return null;
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

    /**
     * JObject remove from a map the value reachable by key
     *
     * @param parent value of parent node if it exists
     * @param key    value used as a key in the map
     * @return value if key exists else null
     */
    default Object remove(JNode parent, String key) {
        return null;
    }

    int size();

    boolean isEmpty();

    void clear();

    String path();

    void path(String path);
}
