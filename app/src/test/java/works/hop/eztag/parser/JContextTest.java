package works.hop.eztag.parser;

import org.junit.jupiter.api.Test;
import works.hop.eztag.pubsub.JEventReceiver;

import javax.xml.stream.XMLStreamException;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toCollection;

class JContextTest {

    @Test
//    @Disabled("not yet ready for testing")
    void signal_data_changed_event_to_generated_component() throws XMLStreamException {
        // template
        String template = """
                <x-ul x-items="($ in users if $.name == 'jimmy')[0].numbers" x-key="num">
                    <x-li x-if="num%3==0">
                        <x-span x-text="num"></x-span>
                    </x-li>
                </x-ul>
                """;

        // create hierarchy of model objects
        JObject model = new JObject();  // root of model object
        JArray users = new JArray();
        JObject user = new JObject();

        JArray numbers = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
                .map(numNode -> {
                    JObject obj = new JObject();
                    obj.put("num", numNode);
                    return obj;
                }).collect(toCollection(JArray::new));

        user.put("numbers", numbers);
        user.put("name", "jimmy");
        users.add(user);
        model.put("users", users);

        // create hierarchy of markup elements
        JContext context = new JContext(model, new JEventReceiver());
        JParser parser = new JParser(context);
        JElement root = parser.parse(template);
        System.out.println(context.process(root));

        // link the two hierarchies using the same observable (context): the tree hierarchy needs to react to data changes in the model hierarchy
        model.observer(context);

        // now add another item
        JObject item15 = new JObject();
        item15.put("num", 15);
        numbers.add(item15);

        // now update num 9 to be 10
        numbers.update(i -> (int) ((JNodeObject) i).get("num") == 9, node -> {
            ((JNodeObject)node).put("num", 10);
        });

        // now remove 3
        numbers.removeFirst(i -> (int) ((JNodeObject) i).get("num") == 3);
    }

}