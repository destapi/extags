package works.hop.eztag.parser;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class JArray extends LinkedList<Object> implements JNode {

    JObserver observer;
    JNode parent;
    String path;

    @Override
    public boolean isArray() {
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
    public Object getIndexed(String subscriber, int index) {
        return super.get(index);
    }

    @Override
    public Object getItem(String subscriber, Predicate<Object> predicate) {
        return super.stream().filter(predicate).findFirst().orElseThrow();
    }

    @Override
    public Object replaceIndexed(int index, Object value) {
        JObserver rootObserver = this.root().observer();
        if (rootObserver != null) {
            rootObserver.updateItemInCollection(this, this.tracePath(), super.get(index), value);
        }
        return super.set(index, value);
    }

    @Override
    public void replaceItem(Predicate<Object> predicate, Object value) {
        for (int i = 0; i < super.size(); i++) {
            if (predicate.test(super.get(i))) {
                super.set(i, value);
                // notify with first match
                JObserver rootObserver = this.root().observer();
                if (rootObserver != null) {
                    rootObserver.updateItemInCollection(this, this.tracePath(), super.get(i), value);
                }
                break;
            }
        }
    }

    @Override
    public boolean addItem(Object value) {
        boolean added = add(value);
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
    public void updateItem(Predicate<Object> predicate, Consumer<JNode> consumer) {
        for (Iterator<Object> iterator = super.iterator(); iterator.hasNext(); ) {
            Object next = iterator.next();
            if (predicate.test(next)) {
                consumer.accept((JNode) next);
                // notify using first match
                JObserver rootObserver = this.root().observer();
                if (rootObserver != null) {
                    rootObserver.updateItemInCollection(this, this.tracePath(), null, next);
                }
            }
        }
    }

    @Override
    public Object removeIndexed(int index) {
        Object value = remove(index);
        //notify matched index
        JObserver rootObserver = this.root().observer();
        if (rootObserver != null) {
            rootObserver.removeItemFromCollection(this, this.tracePath(), index, value);
        }
        return value;
    }

    @Override
    public Object removeFirst(Predicate<Object> predicate) {
        for (Iterator<Object> iterator = super.iterator(); iterator.hasNext(); ) {
            Object next = iterator.next();
            if (predicate.test(next)) {
                iterator.remove();
                // notify using first match
                JObserver rootObserver = this.root().observer();
                if (rootObserver != null) {
                    rootObserver.removeItemFromCollection(this, this.tracePath(), next);
                }
                return next;
            }
        }
        return null;
    }

    @Override
    public void removeAny(Predicate<Object> predicate) {
        for (Iterator<Object> iterator = super.iterator(); iterator.hasNext(); ) {
            Object next = iterator.next();
            if (predicate.test(next)) {
                iterator.remove();
                //notify using any match
                JObserver rootObserver = this.root().observer();
                if (rootObserver != null) {
                    rootObserver.removeItemFromCollection(this, this.tracePath(), next);
                }
            }
        }
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
