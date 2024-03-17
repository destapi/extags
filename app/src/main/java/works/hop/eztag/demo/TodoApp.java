package works.hop.eztag.demo;

import com.google.gson.Gson;
import works.hop.eztag.server.handler.todo.DeleteTodo;
import works.hop.eztag.server.handler.todo.GetAllTodos;
import works.hop.eztag.server.handler.todo.ToggleTodo;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static works.hop.eztag.server.App.runApp;

public class TodoApp {

    public static final Gson gson = new Gson();
    public static List<Map<String, Object>> todos = new LinkedList<>();

    public static void main(String[] args) {
        runApp(args, (app) -> {
            app.route("/todos")
                    .delete("/", new DeleteTodo(gson, todos))
                    .put("/", new ToggleTodo(gson, todos))
                    .post("/", new GetAllTodos(gson, todos))
                    .get("/", new ToggleTodo(gson, todos));
        });
    }
}
