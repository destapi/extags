package works.hop.eztag.server.handler.todo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import works.hop.eztag.server.handler.ReqHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

// curl -X POST "http://localhost:8080/htmx/" -H "Content-Type: application/json" -d "{\"title\": \"Read my book\"}"
public class CreateXTodo extends ReqHandler {

    final List<Map<String, Object>> todos;
    final Function<Map<String, Object>, String> listItemTemplate;

    public CreateXTodo(List<Map<String, Object>> todos, Function<Map<String, Object>, String> listItemTemplate) {
        this.todos = todos;
        this.listItemTemplate = listItemTemplate;
    }

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        try {
            String title = request.getParameter("title");
            Map<String, Object> newTodo = new HashMap<>();
            newTodo.put("title", title);
            newTodo.put("id", UUID.randomUUID().toString());
            newTodo.put("done", false);
            todos.add(newTodo);

            response.setStatus(201);
            response.setContentType("text/html");
            return todos.stream().map(listItemTemplate)
                    .collect(Collectors.joining("\n"));
        } catch (Exception e) {
            response.setStatus(500);
            return e.getLocalizedMessage();
        }
    }
}
