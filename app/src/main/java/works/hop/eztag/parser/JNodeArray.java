package works.hop.eztag.parser;

import works.hop.eztag.pubsub.JSubscribe;

import java.util.LinkedList;
import java.util.List;

import static works.hop.eztag.pubsub.JReceiver.interests;

public abstract class JNodeArray extends LinkedList<Object> implements JNode {

    public abstract JObserver observer();

    public abstract void observer(JObserver observer);

    public abstract JNode parent();

    public abstract void parent(JNode parent);

    public abstract String path();

    public abstract void path(String path);

    @Override
    public Object get(int index) {
        JSubscribe subscriber = new JSubscribe(interests, this);
        JObserver rootObserver = this.root().observer();
        if (this.observer() != null) {
            rootObserver.subscribe(List.of(subscriber));
        }
        return super.get(index);
    }

    @Override
    public Object set(int index, Object value) {
        JObserver rootObserver = this.root().observer();
        if (rootObserver != null) {
            rootObserver.updateItemInCollection(this, this.tracePath(), super.get(index), value);
        }
        return super.set(index, value);
    }

    @Override
    public boolean add(Object value) {
        boolean added = super.add(value);
        // apply parent-child relationship
        if (JNode.class.isAssignableFrom(value.getClass())) {
            ((JNode) value).parent(this);
        }

        // notify with added value
        JObserver rootObserver = this.root().observer();
        if (added && rootObserver != null) {
            rootObserver.addItemToCollection(this, this.tracePath(), value);
        }
        return added;
    }

    @Override
    public Object remove(int index) {
        Object value = super.remove(index);
        //notify matched index
        JObserver rootObserver = this.root().observer();
        if (rootObserver != null) {
            rootObserver.removeItemFromCollection(this, this.tracePath(), index, value);
        }
        return value;
    }
}
