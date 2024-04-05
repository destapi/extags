package works.hop.eztag.parser;

import works.hop.eztag.pubsub.JSubscribe;

import java.util.LinkedHashMap;
import java.util.List;

import static works.hop.eztag.pubsub.JReceiver.interests;

public abstract class JNodeObject extends LinkedHashMap<String, Object> implements JNode {

    public abstract JObserver observer();

    public abstract void observer(JObserver observer);

    public abstract JNode parent();

    public abstract void parent(JNode parent);

    public abstract String path();

    public abstract void path(String path);

    @Override
    public Object get(Object key) {
        JSubscribe subscriber = new JSubscribe(interests, this);
        JObserver rootObserver = this.root().observer();
        if (this.observer() != null) {
            rootObserver.subscribe(List.of(subscriber));
        }
        return super.get(key);
    }

    @Override
    public Object put(String key, Object value) {
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
            if (oldValue == null) {
                rootObserver.addItemToDictionary(this, this.tracePath(), key, oldValue, value);
            } else {
                rootObserver.updateItemInDictionary(this, this.tracePath(), key, oldValue, value);
            }
        }

        return super.put(key, value);
    }

    @Override
    public Object replace(String key, Object value) {
        JObserver rootObserver = this.root().observer();
        if (rootObserver != null) {
            rootObserver.updateItemInCollection(this, this.tracePath(), key, value);
        }

        return super.replace(key, value);
    }

    @Override
    public Object remove(Object key) {
        Object value = super.remove(key);
        JObserver rootObserver = this.root().observer();
        if (rootObserver != null) {
            rootObserver.removeItemFromDictionary(this, this.tracePath(), (String)key, value);
        }
        return value;
    }
}
