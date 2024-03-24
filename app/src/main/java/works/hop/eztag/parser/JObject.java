package works.hop.eztag.parser;

import works.hop.eztag.pubsub.JSubscribe;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Consumer;

import static works.hop.eztag.pubsub.JReceiver.interests;

public class JObject extends LinkedHashMap<String, Object> implements JNode {

    JObserver observer;
    JNode parent;
    String path;

    @Override
    public boolean isObject() {
        return true;
    }

    @Override
    public JObserver observer() {
        return this.observer;
    }

    @Override
    public void observer(JObserver observer) {
        this.observer = observer;
    }

    @Override
    public JNode parent() {
        return this.parent;
    }

    @Override
    public void parent(JNode parent) {
        this.parent = parent;
    }

    @Override
    public Object getItem(String key) {
        JSubscribe subscriber = new JSubscribe(interests, this);
        if(this.observer != null) {
            this.observer.subscribe(List.of(subscriber));
        }
        return super.get(key);
    }

    @Override
    public void putItem(String key, Object value) {
        // apply paren-child relationship
        Object oldValue = get(key);
        if (value != null) {
            if (JNode.class.isAssignableFrom(value.getClass())) {
                ((JNode) value).parent(this);
                ((JNode) value).path(".".concat(key));
            }
        }

        // notify with added value
        JObserver rootObserver = this.root().observer();
        if (rootObserver != null) {
            if(oldValue == null) {
                rootObserver.addItemToDictionary(this, this.tracePath(), key, oldValue, value);
            }
            else{
                rootObserver.updateItemInDictionary(this, this.tracePath(), key, oldValue, value);
            }
        }

        super.put(key, value);
    }

    @Override
    public void update(JNode parent, String key, Consumer<JNode> consumer) {
        Object value = get(key);
        consumer.accept((JNode) value);

        JObserver rootObserver = this.root().observer();
        if (rootObserver != null) {
            rootObserver.updateItemInCollection(this, this.tracePath(), key, value);
        }
    }

    @Override
    public void replaceItem(String key, Object value) {
        JObserver rootObserver = this.root().observer();
        if (rootObserver != null) {
            rootObserver.updateItemInCollection(this, this.tracePath(), key, value);
        }

        super.replace(key, value);
    }

    @Override
    public Object remove(JNode parent, String key) {
        Object value = remove(key);
        JObserver rootObserver = this.root().observer();
        if (rootObserver != null) {
            rootObserver.removeItemFromDictionary(this, this.tracePath(), key, value);
        }
        return value;
    }

    @Override
    public String path() {
        return this.path;
    }

    @Override
    public void path(String path) {
        this.path = path;
    }
}
