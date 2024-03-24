package works.hop.eztag.pubsub;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import works.hop.eztag.parser.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static works.hop.eztag.pubsub.JReceiver.interests;

class JSubscribeTest {

    @Test
    void verify_updating_item_in_object_triggers_event() {
        //create a root model
        JArray array = new JArray();
        for (int i = 0; i < 5; i++) {
            JObject node = new JObject();
            node.putItem(Integer.toString(i), 1);
            array.addItem(node);
        }

        // create subscriber having a default receiver and assign to root node
        JReceiver receiver = mock(JReceiver.class);
        JObserver observer = new JContext(array, receiver);
        array.observer(observer);

        //subscribe (implicitly) to property in a model
        JObject node = (JObject) array.getIndexed(1);

        //make change to data in a model
        node.putItem("1", 100);

        //at this point, the receiver should receive the event data
        ArgumentCaptor<JEvent> eventCaptor = ArgumentCaptor.forClass(JEvent.class);
        ArgumentCaptor<Object> oldValueCaptor = ArgumentCaptor.forClass(int.class);
        ArgumentCaptor<Object> newValueCaptor = ArgumentCaptor.forClass(int.class);
        verify(receiver).onEvent(eventCaptor.capture(), oldValueCaptor.capture(), newValueCaptor.capture());

        //assert received data
        assertThat(oldValueCaptor.getValue()).isEqualTo(1);
        assertThat(newValueCaptor.getValue()).isEqualTo(100);
    }

    @Test
    void verify_adding_item_into_array_triggers_event() {
        //create a root model
        JArray array = new JArray();
        for (int i = 0; i < 5; i++) {
            JObject node = new JObject();
            node.putItem(Integer.toString(i), 1);
            array.addItem(node);
        }

        // create subscriber having a default receiver and assign to root node
        JReceiver receiver = mock(JReceiver.class);
        JObserver observer = new JContext(array, receiver);
        array.observer(observer);

        // subscribe (explicit) to model events
        JSubscribe subscriber = new JSubscribe(interests, array);
        observer.subscribe(List.of(subscriber));

        //add new item to the model
        JObject node6 = new JObject();
        node6.putItem("6", 6);
        array.addItem(node6);

        // capture arguments
        ArgumentCaptor<JEvent> eventCaptor = ArgumentCaptor.forClass(JEvent.class);
        ArgumentCaptor<Object> oldValueCaptor = ArgumentCaptor.forClass(JNode.class);
        ArgumentCaptor<Object> newValueCaptor = ArgumentCaptor.forClass(JNode.class);
        verify(receiver).onEvent(eventCaptor.capture(), oldValueCaptor.capture(), newValueCaptor.capture());

        //assert received data
        assertThat(oldValueCaptor.getValue()).isNull();
        assertThat(((JNode)newValueCaptor.getValue()).getItem("6")).isEqualTo(6);
    }
}