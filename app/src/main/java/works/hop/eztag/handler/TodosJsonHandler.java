package works.hop.eztag.handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.*;

public class TodosJsonHandler extends AbstractHandler {

    public static final Gson gson = new Gson();
    public static List<Map<String, Object>> todos = new LinkedList<>();

    //  curl -X DELETE "http://localhost:8080/todos/?id=<id>"
    private static void deleteTodo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        for (Iterator<Map<String, Object>> iter = todos.iterator(); iter.hasNext(); ) {
            if (iter.next().get("id").equals(id)) {
                iter.remove();
                break;
            }
        }
        response.setStatus(200);
        response.getWriter().write(gson.toJson(todos));
    }

    // curl -X PUT "http://localhost:8080/todos/?id=<id>"
    private static void toggleTodo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        Map<String, Object> todo = todos.stream().filter(t -> t.get("id").equals(id)).findFirst().get();
        todo.put("done", !((Boolean) todo.get("done")));
        response.setStatus(201);
        response.getWriter().write(gson.toJson(todos));
    }

    // curl "http://localhost:8080/todos/"
    private static void getAllTodos(HttpServletResponse response) throws IOException {
        response.setStatus(200);
        response.getWriter().write(gson.toJson(todos));
    }

    // curl -X POST "http://localhost:8080/todos/" -H "Content-Type: application/json" -d "{\"title\": \"Read my book\"}"
    private static void createTodo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Type mapType = new TypeToken<Map<String, Object>>() {
        }.getType();
        Map<String, Object> body = gson.fromJson(new InputStreamReader(request.getInputStream()), mapType);
        body.put("id", UUID.randomUUID().toString());
        body.put("done", false);
        todos.add(body);
        response.setStatus(201);
        response.getWriter().write(gson.toJson(todos));
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String method = baseRequest.getMethod();
        try {
            switch (method) {
                case "post":
                case "POST": {
                    createTodo(request, response);
                    break;
                }
                case "get":
                case "GET": {
                    getAllTodos(response);
                    break;
                }
                case "put":
                case "PUT": {
                    toggleTodo(request, response);
                    break;
                }
                case "delete":
                case "DELETE": {
                    deleteTodo(request, response);
                    break;
                }
                default: {
                    throw new RuntimeException("Unexpected request method");
                }
            }
        } finally {
            baseRequest.setHandled(true);
        }
    }
}
