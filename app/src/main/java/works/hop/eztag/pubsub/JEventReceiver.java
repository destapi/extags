package works.hop.eztag.pubsub;

import works.hop.eztag.parser.JElement;

public class JEventReceiver implements JReceiver{

    public void onEvent(JEvent event, Object oldValue, Object newValue) {
        System.out.printf("JEventReceiver - interest: %s, element: %s, element path: %s, old value: %s, new value: %s\n",
                event.getEvent(),
                ((JElement)event.getSource()).getTagName(),
                event.getPath(),
                oldValue != null? oldValue.toString() : null,
                newValue != null? newValue.toString() : null);
    }
}
