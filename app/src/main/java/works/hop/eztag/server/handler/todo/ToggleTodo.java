package works.hop.eztag.server.handler.todo;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import works.hop.eztag.server.handler.ReqHandler;

import java.util.List;
import java.util.Map;

// curl -X PUT "http://localhost:8080/todos/?id=<id>"
public class ToggleTodo extends ReqHandler {

    final Gson gson;
    final List<Map<String, Object>> todos;

    public ToggleTodo(Gson gson, List<Map<String, Object>> todos) {
        this.gson = gson;
        this.todos = todos;
    }

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        Map<String, Object> todo = todos.stream().filter(t -> t.get("id").equals(id)).findFirst().get();
        todo.put("done", !((Boolean) todo.get("done")));
        response.setStatus(201);
        return gson.toJson(todos);
    }
}
