package works.hop.eztag.demo;

import org.mvel2.templates.TemplateRuntime;
import works.hop.eztag.server.handler.todo.CreateXTodo;
import works.hop.eztag.server.handler.todo.DeleteXTodo;
import works.hop.eztag.server.handler.todo.GetXAllTodos;
import works.hop.eztag.server.handler.todo.ToggleXTodo;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import static works.hop.eztag.server.App.runApp;

public class TodoXApp {

    public static List<Map<String, Object>> todos = new LinkedList<>();
    public static Function<Map<String, Object>, String> listItemTemplate = (map) -> {
        String template = "/todos/fragment/todo-list.xml";
        return TemplateRuntime.eval(Objects.requireNonNull(TodoXApp.class.getResourceAsStream(template)), map).toString();
    };

    public static void main(String[] args) {
        runApp(args, (app) -> {
            app.route("/htmx")
                    .delete("/", new DeleteXTodo(todos, listItemTemplate))
                    .put("/", new ToggleXTodo(todos, listItemTemplate))
                    .post("/", new CreateXTodo(todos, listItemTemplate))
                    .get("/", new GetXAllTodos(todos, listItemTemplate));
        });
    }
}
