package works.hop.eztag.parser;

import java.util.function.Consumer;

public class JObject extends JNodeObject {

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
    public String path() {
        return this.path;
    }

    @Override
    public void path(String path) {
        this.path = path;
    }

    @Override
    public void update(String key, Consumer<JNode> consumer) {
        Object value = super.get(key);
        consumer.accept((JNode) value);

        JObserver rootObserver = this.root().observer();
        if (rootObserver != null) {
            rootObserver.updateItemInCollection(this, this.tracePath(), key, value);
        }
    }
}
