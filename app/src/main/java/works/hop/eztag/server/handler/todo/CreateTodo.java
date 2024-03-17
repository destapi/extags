package works.hop.eztag.server.handler.todo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import works.hop.eztag.server.handler.ReqHandler;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.UUID;

// curl -X POST "http://localhost:8080/todos/" -H "Content-Type: application/json" -d "{\"title\": \"Read my book\"}"
public class CreateTodo extends ReqHandler {

    final Gson gson;
    final List<Map<String, Object>> todos;

    public CreateTodo(Gson gson, List<Map<String, Object>> todos) {
        this.gson = gson;
        this.todos = todos;
    }

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        try {
            Type mapType = new TypeToken<Map<String, Object>>() {
            }.getType();
            Map<String, Object> body = gson.fromJson(new InputStreamReader(request.getInputStream()), mapType);
            body.put("id", UUID.randomUUID().toString());
            body.put("done", false);
            todos.add(body);
            response.setStatus(201);
            return gson.toJson(todos);
        } catch (Exception e) {
            response.setStatus(500);
            return e.getLocalizedMessage();
        }
    }
}
