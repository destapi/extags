package works.hop.eztag.parser;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JContext implements JObserver {

    Map<String, List<JNode>> observables = new ConcurrentHashMap<>();
    Object context;

    public JContext(Object context) {
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
    public void onEvent(JEvent event, JNode data) {
        System.out.println(process((JElement) event.source));
    }

    @Override
    public void add(Object target, String path, Object value) {
        List<JNode> listeners = observables.get("add");
        for(JNode node : listeners){
            JEvent event = new JEvent();
            event.source = node;
            event.event = "add";
            node.bubble(event, (JNode) value);
        }
    }
}