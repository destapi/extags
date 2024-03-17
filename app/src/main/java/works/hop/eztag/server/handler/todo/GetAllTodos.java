package works.hop.eztag.server.handler.todo;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import works.hop.eztag.server.handler.ReqHandler;

import java.util.List;
import java.util.Map;

// curl "http://localhost:8080/todos/"
public class GetAllTodos extends ReqHandler {

    final Gson gson;
    final List<Map<String, Object>> todos;

    public GetAllTodos(Gson gson, List<Map<String, Object>> todos) {
        this.gson = gson;
        this.todos = todos;
    }

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(200);
        return gson.toJson(todos);
    }
}
