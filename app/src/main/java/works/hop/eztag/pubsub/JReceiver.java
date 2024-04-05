package works.hop.eztag.pubsub;

import java.io.Serializable;

public interface JReceiver extends Serializable {

    String[] interests = {"add", "update", "delete"};

    default void onEvent(JEvent event, Object oldValue, Object newValue) {
        System.out.printf("event: %s, old: %s, new: %s\n", event.getEvent(),
                oldValue != null? oldValue.toString() : null,
                newValue != null? newValue.toString() : null);
    }
}
