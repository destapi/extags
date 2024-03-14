package works.hop.eztag.server.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.mvel2.templates.TemplateRuntime;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TodosHtmxHandler extends AbstractHandler {

    public static List<Map<String, Object>> todos = new LinkedList<>();
    public static Function<Map<String, Object>, String> listItemTemplate = (map) -> {
        String template = "/todos/fragment/todo-list.xml";
        return TemplateRuntime.eval(Objects.requireNonNull(TodosHtmxHandler.class.getResourceAsStream(template)), map).toString();
    };

    //  curl -X DELETE "http://localhost:8080/htmx/?id=<id>"
    private static void deleteTodo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        for (Iterator<Map<String, Object>> iter = todos.iterator(); iter.hasNext(); ) {
            if (iter.next().get("id").equals(id)) {
                iter.remove();
                break;
            }
        }
        response.setStatus(200);
        response.setContentType("text/html");
        response.getWriter().write(todos.stream().map(todo -> listItemTemplate.apply(todo))
                .collect(Collectors.joining("\n")));
    }

    // curl -X PUT "http://localhost:8080/htmx/?id=<id>"
    private static void toggleTodo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        Map<String, Object> existingTodo = todos.stream().filter(t -> t.get("id").equals(id)).findFirst().get();
        existingTodo.put("done", !((Boolean) existingTodo.get("done")));
        response.setStatus(201);
        response.setContentType("text/html");
        response.getWriter().write(todos.stream().map(todo -> listItemTemplate.apply(todo))
                .collect(Collectors.joining("\n")));
    }

    // curl "http://localhost:8080/htmx/"
    private static void getAllTodos(HttpServletResponse response) throws IOException {
        response.setStatus(200);
        response.setContentType("text/html");
        response.getWriter().write(todos.stream().map(todo -> listItemTemplate.apply(todo))
                .collect(Collectors.joining("\n")));
    }

    // curl -X POST "http://localhost:8080/htmx/" -H "Content-Type: application/json" -d "{\"title\": \"Read my book\"}"
    private static void createTodo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String title = request.getParameter("title");
        Map<String, Object> newTodo = new HashMap<>();
        newTodo.put("title", title);
        newTodo.put("id", UUID.randomUUID().toString());
        newTodo.put("done", false);
        todos.add(newTodo);

        response.setStatus(201);
        response.setContentType("text/html");
        response.getWriter().write(todos.stream().map(todo -> listItemTemplate.apply(todo))
                .collect(Collectors.joining("\n")));
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
