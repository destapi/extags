package works.hop.eztag.parser;

import java.util.function.Supplier;

public class JSubscribe {

    String event;
    Supplier<?> unsubscribe;
    JNode target;

    public JSubscribe(String event, JElement target) {
        this.event = event;
        this.target = target;
    }
}
