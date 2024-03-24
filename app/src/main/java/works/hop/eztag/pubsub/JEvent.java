package works.hop.eztag.pubsub;

import works.hop.eztag.parser.JNode;

public class JEvent {

    private String event;
    private JNode source;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public JNode getSource() {
        return source;
    }

    public void setSource(JNode source) {
        this.source = source;
    }
}
