package works.hop.eztag.parser;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JContext implements JObserver {

    final JReceiver receiver;
    final Object context;
    final Map<String, List<JNode>> observables = new ConcurrentHashMap<>();

    public JContext(Object context) {
        this(context, null);
    }

    public JContext(Object context, JReceiver receiver) {
        this.receiver = receiver;
        this.context = context;
    }

    public String process(JElement element) {
        element.setContext(context);
        return element.render();
    }

    @Override
    public Map<String, List<JNode>> observables() {
        return this.observables;
    }

    @Override
    public JReceiver receiver() {
        return this.receiver;
    }

    @Override
    public void subscribe(List<JSubscribe> interests) {
        interests.forEach(sub -> {
            if (!observables().containsKey(sub.event)) {
                observables().put(sub.event, new LinkedList<>());
            }
            observables().get(sub.event).add(sub.target);
            sub.unsubscribe = () -> observables().get(sub.event).remove(sub.target);
        });
    }

    @Override
    public void addItemToCollection(Object target, String path, Object value) {
        List<JNode> listeners = observables.get("add");
        for (JNode node : listeners) {
            JEvent event = new JEvent();
            event.source = node;
            event.event = "addItemToCollection";
            node.bubble(event, (JNode) value);
        }
    }

    @Override
    public void removeItemFromCollection(Object target, String path, Object value) {
        List<JNode> listeners = observables.get("remove");
        for (JNode node : listeners) {
            JEvent event = new JEvent();
            event.source = node;
            event.event = "removeItemFromCollection";
            node.bubble(event, (JNode) value);
        }
    }

    @Override
    public void updateItemInCollection(Object target, String path, Object prev, Object value) {
        List<JNode> listeners = observables.get("update");
        for (JNode node : listeners) {
            JEvent event = new JEvent();
            event.source = node;
            event.event = "updateItemInCollection";
            node.bubble(event, (JNode) value);
        }
    }
}