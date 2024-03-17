package works.hop.eztag.server.handler.todo;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import works.hop.eztag.server.handler.ReqHandler;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

//  curl -X DELETE "http://localhost:8080/todos/?id=<id>"
public class DeleteTodo extends ReqHandler {

    final Gson gson;
    final List<Map<String, Object>> todos;

    public DeleteTodo(Gson gson, List<Map<String, Object>> todos) {
        this.gson = gson;
        this.todos = todos;
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
        return gson.toJson(todos);
    }
}
