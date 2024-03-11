package works.hop.eztag.template;

import works.hop.eztag.parser.JContext;
import works.hop.eztag.parser.JElement;
import works.hop.eztag.parser.JParser;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TodoTemplates {

    public static String resourceFolder = "www/todos";

    public static void main(String[] args) throws XMLStreamException, IOException {
        List<Map<String, Object>> todos = new LinkedList<>();
        todos.add(Map.of("id", 1, "title", "Read book", "done", false));
        todos.add(Map.of("id", 2, "title", "Make dinner", "done", true));
        JContext processor = new JContext(Map.of("todos", todos));
        JParser parser = new JParser(String.format("/todos/%s.xml", "todo-app"), processor);
        JElement root = parser.parse();
        String markup = processor.process(root);
        Files.writeString(Path.of(resourceFolder, "index.html"), markup,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);

    }
}
