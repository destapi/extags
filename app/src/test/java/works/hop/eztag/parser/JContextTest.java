package works.hop.eztag.parser;

import org.junit.jupiter.api.Test;

import javax.xml.stream.XMLStreamException;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toCollection;

class JContextTest {

    @Test
//    @Disabled("not yet ready for testing")
    void signal_data_changed_event_to_generated_component() throws XMLStreamException {
        // template
        String template = """
                <x-ul x-items="($ in users if $.name == 'jimmy')[0].numbers" x-key="num" x-sub="add,remove,update">
                    <x-li x-if="num%3==0">
                        <x-span x-text="num"></x-span>
                    </x-li>
                </x-ul>
                """;

        // create model object and set parent-child relationships
        JObject model = new JObject();  // root of model object
        JArray users = new JArray();
        users.parent(model);    //set parent-child relationship

        JObject user = new JObject();
        user.parent(users); //set parent-child relationship

        JArray numbers = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
                .map(n -> {
                    JObject obj = new JObject();
                    obj.put("num", n);
                    return obj;
                }).collect(toCollection(JArray::new));
        numbers.parent(user);    //set parent-child relationship
        numbers.forEach(num -> ((JNode) num).parent(numbers));   //set parent-child relationship

        user.put("numbers", numbers);
        user.put("name", "jimmy");
        users.add(user);
        model.put("users", users);

        // context
        JContext context = new JContext(model, new JReceiver() {
        });
        JParser parser = new JParser(context);
        JElement root = parser.parse(template);
        System.out.println(context.process(root));

        // set observer
        model.observer(context);

        // now add another item
        JObject item15 = new JObject();
        item15.put("num", 15);
        numbers.addItem(item15);

        // now update num 9 to be 10
        numbers.updateItem(i -> (int) ((JNode) i).getItem("", "num") == 9, node -> {
            node.putItem("num", 10);
        });

        // now remove 3
        numbers.removeFirst(i -> (int) ((JNode) i).getItem("", "num") == 3);
    }

}