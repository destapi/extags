package works.hop.eztag.parser;

public interface JReceiver {

    default void onEvent(JEvent event, JNode data) {
        System.out.println(data.toString());
    }
}
