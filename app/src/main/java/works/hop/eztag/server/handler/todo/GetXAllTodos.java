package works.hop.eztag.server.handler.todo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import works.hop.eztag.server.handler.ReqHandler;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

// curl "http://localhost:8080/htmx/"
public class GetXAllTodos extends ReqHandler {

    final List<Map<String, Object>> todos;
    final Function<Map<String, Object>, String> listItemTemplate;

    public GetXAllTodos(List<Map<String, Object>> todos, Function<Map<String, Object>, String> listItemTemplate) {
        this.todos = todos;
        this.listItemTemplate = listItemTemplate;
    }

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(200);
        response.setContentType("text/html");
        return todos.stream().map(listItemTemplate)
                .collect(Collectors.joining("\n"));
    }
}
