package works.hop.eztag.server.handler.todo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import works.hop.eztag.server.handler.ReqHandler;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

// curl -X PUT "http://localhost:8080/htmx/?id=<id>"
public class ToggleXTodo extends ReqHandler {

    final List<Map<String, Object>> todos;
    final Function<Map<String, Object>, String> listItemTemplate;

    public ToggleXTodo(List<Map<String, Object>> todos, Function<Map<String, Object>, String> listItemTemplate) {
        this.todos = todos;
        this.listItemTemplate = listItemTemplate;
    }

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        Map<String, Object> existingTodo = todos.stream().filter(t -> t.get("id").equals(id)).findFirst().get();
        existingTodo.put("done", !((Boolean) existingTodo.get("done")));
        response.setStatus(201);
        response.setContentType("text/html");
        return todos.stream().map(listItemTemplate)
                .collect(Collectors.joining("\n"));
    }
}
