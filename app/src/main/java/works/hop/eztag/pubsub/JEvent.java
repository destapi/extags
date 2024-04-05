package works.hop.eztag.pubsub;

import works.hop.eztag.parser.JNode;

public class JEvent {

    private String event;
    private String path;
    private JNode source;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public JNode getSource() {
        return source;
    }

    public void setSource(JNode source) {
        this.source = source;
    }
}
