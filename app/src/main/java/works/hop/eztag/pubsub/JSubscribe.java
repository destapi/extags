package works.hop.eztag.pubsub;

import works.hop.eztag.parser.JNode;

import java.io.Serializable;
import java.util.function.Supplier;

public class JSubscribe implements Serializable {

    String[] interests;
    Supplier<?> unsubscribe;
    JNode target;
    String path;

    public JSubscribe(String[] interests, JNode target) {
        this(interests, target, target.tracePath());
    }

    public JSubscribe(String[] interests, JNode target, String path) {
        this.interests = interests;
        this.target = target;
        this.path = path;
    }

    public String[] getInterests() {
        return interests;
    }

    public void setInterests(String[] interests) {
        this.interests = interests;
    }

    public Supplier<?> getUnsubscribe() {
        return unsubscribe;
    }

    public void setUnsubscribe(Supplier<?> unsubscribe) {
        this.unsubscribe = unsubscribe;
    }

    public JNode getTarget() {
        return target;
    }

    public void setTarget(JNode target) {
        this.target = target;
    }
}
