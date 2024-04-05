package works.hop.eztag.parser;

import works.hop.eztag.clone.JCopy;
import works.hop.eztag.pubsub.JSubscribe;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static works.hop.eztag.pubsub.JReceiver.interests;

public class JArray extends JNodeArray {

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
    public String path() {
        return this.path;
    }

    @Override
    public void path(String path) {
        this.path = path;
    }

    @Override
    public Object getItem(Predicate<Object> predicate) {
        JSubscribe subscriber = new JSubscribe(interests, this);
        JObserver rootObserver = this.root().observer();
        if (this.observer != null) {
            rootObserver.subscribe(List.of(subscriber));
        }
        return super.stream().filter(predicate).findFirst().orElseThrow();
    }

    @Override
    public void update(Predicate<Object> predicate, Object value) {
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
    public void update(Predicate<Object> predicate, Consumer<JNode> consumer) {
        for (Iterator<Object> iterator = super.iterator(); iterator.hasNext(); ) {
            Object next = iterator.next();
            JObject oldValue = JCopy.cloneObject(next, JObject.class);
            if (predicate.test(next)) {
                consumer.accept((JNode) next);
                // notify using first match
                JObserver rootObserver = this.root().observer();
                if (rootObserver != null) {
                    rootObserver.updateItemInCollection(this, this.tracePath(), oldValue, next);
                }
            }
        }
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
}
