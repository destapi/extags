package works.hop.eztag.server.handler.todo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import works.hop.eztag.server.handler.ReqHandler;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

//  curl -X DELETE "http://localhost:8080/htmx/?id=<id>"
public class DeleteXTodo extends ReqHandler {

    final List<Map<String, Object>> todos;
    final Function<Map<String, Object>, String> listItemTemplate;

    public DeleteXTodo(List<Map<String, Object>> todos, Function<Map<String, Object>, String> listItemTemplate) {
        this.todos = todos;
        this.listItemTemplate = listItemTemplate;
    }

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        for (Iterator<Map<String, Object>> iter = todos.iterator(); iter.hasNext(); ) {
            if (iter.next().get("id").equals(id)) {
                iter.remove();
                break;
            }
        }
        response.setStatus(200);
        response.setContentType("text/html");
        return todos.stream().map(listItemTemplate)
                .collect(Collectors.joining("\n"));
    }
}
