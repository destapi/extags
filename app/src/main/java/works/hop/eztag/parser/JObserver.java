package works.hop.eztag.parser;

import works.hop.eztag.pubsub.JReceiver;
import works.hop.eztag.pubsub.JSubscribe;

import java.util.List;
import java.util.Map;

/**
 * JObserver contains methods for accepting events following changes in data from Objects of type JNode. Objects of this type
 * (JArray and JObject) are 'reactive aware', meaning that they are able to know when any of their own attribute values have been
 * changed, either through addition, updating or removal. This capability can be harnessed to using a concrete representation
 * of the Observer, and should be written to any concrete Writer of interest
 */
public interface JObserver {

    Map<String, List<JNode>> observables();

    JReceiver receiver();

    void subscribe(List<JSubscribe> interests);

    /**
     * applies to jarray
     *
     * @param target represents the immediate parent of the JSON attribute whose data is being changed
     * @param path   represents the 'json-path' string which follows the path from the element being changed up to the target or root node.
     * @param value  the value that was added to the array
     */
    default void addItemToCollection(Object target, String path, Object value) {
    }

    /**
     * applies to jobject
     *
     * @param target   represents the immediate parent of the JSON attribute whose data is being changed
     * @param path     represents the 'json-path' string which follows the path from the element being changed up to the target or root node.
     * @param key      key used to identify a value in a dictionary
     * @param oldValue the value identified by the key 'key' in the dictionary before the update
     * @param newValue the new value that will be identified by the key 'key' in the dictionary
     */
    default void addItemToDictionary(Object target, String path, String key, Object oldValue, Object newValue) {
    }

    /**
     * applies to jarray - retrieve from an array using a predicate function
     *
     * @param target represents the immediate parent of the JSON attribute whose data is being changed
     * @param path   represents the 'json-path' string which follows the path from the element being changed up to the target or root node.
     * @param value  value in an array that has been accessed
     */
    default void getItemFromCollection(Object target, String path, Object value) {
    }

    /**
     * applies to jarray
     *
     * @param target represents the immediate parent of the JSON attribute whose data is being changed
     * @param path   represents the 'json-path' string which follows the path from the element being changed up to the target or root node.
     * @param index  ordinal position of the target value in the array
     * @param value  value in an array that has been accessed
     */
    default void getItemFromCollection(Object target, String path, int index, Object value) {
    }

    /**
     * applies to jobject
     *
     * @param target represents the immediate parent of the JSON attribute whose data is being changed
     * @param path   represents the 'json-path' string which follows the path from the element being changed up to the target or root node.
     * @param key    key used to identify a value in a dictionary
     * @param value  value in the dictionary that has been accessed
     */
    default void getItemFromDictionary(Object target, String path, String key, Object value) {
    }

    /**
     * applies to jarray - replace in an array using a predicate function
     *
     * @param target represents the immediate parent of the JSON attribute whose data is being changed
     * @param path   represents the 'json-path' string which follows the path from the element being changed up to the target or root node.
     * @param prev   the value existing before the new one is applied
     * @param value  the value in the array which has been updated
     */
    default void updateItemInCollection(Object target, String path, Object prev, Object value) {
    }

    /**
     * applies to jarray
     *
     * @param target represents the immediate parent of the JSON attribute whose data is being changed
     * @param path   represents the 'json-path' string which follows the path from the element being changed up to the target or root node.
     * @param index  ordinal position of the target value in the array
     * @param prev   the value existing before the new one is applied
     * @param value  the value in the array which has been updated
     */
    default void updateItemInCollection(Object target, String path, int index, Object prev, Object value) {
    }

    /**
     * applies to jobject
     *
     * @param target represents the immediate parent of the JSON attribute whose data is being changed
     * @param path   represents the 'json-path' string which follows the path from the element being changed up to the target or root node.
     * @param key    key used to identify a value in a dictionary
     * @param prev   the value existing before the new one is applied
     * @param value  the value identified by the key 'key' in the dictionary which has been updated
     */
    default void updateItemInDictionary(Object target, String path, String key, Object prev, Object value) {
    }

    /**
     * applies to jarray - removal from an array using a predicate function
     *
     * @param target represents the immediate parent of the JSON attribute whose data is being changed
     * @param path   represents the 'json-path' string which follows the path from the element being changed up to the target or root node.
     * @param value  a predicate function used to search for a target value for deletion in the array
     */
    default void removeItemFromCollection(Object target, String path, Object value) {
    }

    /**
     * applies to jarray
     *
     * @param target represents the immediate parent of the JSON attribute whose data is being changed
     * @param path   represents the 'json-path' string which follows the path from the element being changed up to the target or root node.
     * @param index  ordinal position of the value in the array
     * @param value  value in the array that has been removed
     */
    default void removeItemFromCollection(Object target, String path, int index, Object value) {
    }

    /**
     * applies to jobject
     *
     * @param target represents the immediate parent of the JSON attribute whose data is being changed
     * @param path   represents the 'json-path' string which follows the path from the element being changed up to the target or root node.
     * @param key    key used to identify a value in a dictionary
     * @param value  value in the dictionary that has been removed
     */
    default void removeItemFromDictionary(Object target, String path, String key, Object value) {
    }

    /**
     * @param target unique identifier associated with the root resource undergoing modification
     * @param data   the string output of the change data payload
     */
    default void write(String target, String data) {
    }

    /**
     * @param target unique identifier associated with the root resource undergoing modification
     * @param event  name of event to be associated with the write operation
     * @param data   the string output of the change data payload
     */
    default void write(String target, String event, String data) {
    }
}
