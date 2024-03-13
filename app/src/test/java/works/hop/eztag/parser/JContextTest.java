package works.hop.eztag.parser;

import org.junit.jupiter.api.Test;

import javax.xml.stream.XMLStreamException;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toCollection;

class JContextTest {

    @Test
    void signal_data_changed_event_to_generated_component() throws XMLStreamException {
        // template
        String template = """
                <x-ul x-items="($ in users if $.name == 'jimmy')[0].numbers" x-key="num" x-sub="add,remove,update">
                    <x-li x-if="num%3==0">
                        <x-span x-text="num"></x-span>
                    </x-li>
                </x-ul>
                """;

        // model
        JObject model = new JObject();
        JArray users = new JArray();
        JObject user = new JObject();
        user.parent(users); //set parent-child relationship
        JArray numbers = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
                .map(n -> {
                    JObject obj = new JObject();
                    obj.put("num", n);
                    return obj;
                }).collect(toCollection(JArray::new));
        numbers.forEach(n -> ((JNode)n).parent(numbers));   //set parent-child relationship
        numbers.parent(user);    //set parent-child relationship
        user.put("numbers", numbers);
        user.put("name", "jimmy");
        users.add(user);
        users.parent(model);    //set parent-child relationship
        model.put("users", users);

        // context
        JContext context = new JContext(model);
        JParser parser = new JParser(context);
        JElement root = parser.parse(template);
        System.out.println(context.process(root));

        // set observer
        model.observer(context);

        // now add another item
        JObject item15 = new JObject();
        item15.put("num", 15);
        numbers.addItem(item15);
    }

}