## EzTag - TodoList with HTMX

Picking up from the previous section, the template page will remain exactly the same. That's a fantastic benefit of
using a template

> The layout page (/todos/todo-template.xml)

```xml

<html lang="en">
    <head>
        <meta charset="UTF-8"/>
        <x-title x-slot="title">Todos</x-title>
        <link rel="stylesheet" href="css/style.css" type="text/css"/>
    </head>
    <body>
        <main id="root">
            <x-main x-slot="todo-form"/>

            <x-main x-slot="todo-list"/>

            <x-main x-slot="todo-stats"/>
        </main>

        <footer>&amp;copy; 2024 EzTags</footer>
        <x-script/>
    </body>
</html>
```

> The app page (/todos/todo-htmx-app.xml)

```html

<x-layout x-template="/todos/todo-template.xml">

    <x-doctype x-doctype="&lt;!DOCTYPE html&gt;"/>

    <x-title x-named="title">Todo HTMX App</x-title>

    <x-script src="https://unpkg.com/htmx.org@1.9.10" defer="true"/>

    <x-nav id="todo-stats" x-named="todo-stats">
        <x-div x-eval="true">completed count: @{($ in todos if $.done == true).size()}</x-div>
        <x-button id="clear" x-show="todos.size() != 0" type="button">Clear All</x-button>
    </x-nav>

    <x-form id="todo-form" x-named="todo-form" hx-post="/htmx/" hx-trigger="submit" hx-target="#todo-list">
        <label><input name="title"/></label>
        <button type="submit">Add</button>
    </x-form>

    <x-ul id="todo-list" class="listing" x-named="todo-list" x-items="todos" x-key="id">
        <li>
            <label>
                <x-input type="checkbox" value="@{id}" checked="@{done}" hx-put="/htmx/?id=%s" hx-target="#todo-list"/>
            </label>
            <x-span x-text="title">Read Book</x-span>
            <i class="remove" hx-delete="/htmx/?id=%s" hx-target="#todo-list">x</i>
        </li>
    </x-ul>
</x-layout>
```

The target page:

1. uses the _todo-template.xml_ layout to organize its components using the _named slots_ content
2. injects a _&lt;!doctype&gt;_ into the generated page
3. injects a _title_ to override the default in the generated page
4. injects three named parts (todo-form, todo-list, todo-stats) for the slots in the template page.
5. injects a htmx script tag which the target page requires, frmo the internet and into the _&lt;x-script/&gt;
   placeholder in the template
6. declares event handlers, and these should be available in the document's global scope before they can be attached
   successfully to the target elements

This time around, the glue that will wire together the markup and the user-events generated on the page is automatically
done by _htmx_.

At this juncture, the changes necessary would only have to be in the server-side.

> Template for each new todo

```java
 public static Function<Map<String, Object>, String> listItemTemplate = (map) -> String.format("""
        <li>
            <label><input type="checkbox" value="%s" %s hx-put="/htmx/?id=%s" hx-target="#todo-list" /> </label>
            <span>%s</span>
            <i class="remove" hx-delete="/htmx/?id=%s" hx-target="#todo-list">x</i>
        </li>""", map.get("id"), (boolean) map.get("done") ? "checked" : "", map.get("id"), map.get("title"), map.get("id"));

```

> Refresh all todos

```java
// curl "http://localhost:8080/htmx/"
private static void getAllTodos(HttpServletResponse response) throws IOException {
    response.setStatus(200);
    response.setContentType("text/html");
    response.getWriter().write(todos.stream().map(todo -> listItemTemplate.apply(todo))
            .collect(Collectors.joining("\n")));
}
```

> Create new todo

```java
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
```

> Toggle existing todo

```java
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
```

> Delete todo from list

```java
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
```

And the _todos_ application would continue to work just like it did before when using localStorage. The main difference
is that now the server response type
is now _text/html_ and on the client side, _htmx_ is applying the generated markup to the respective places.

But hold on, wait a minute. Why is there an _listItemTemplate_ markup defined inline, when this could be fetched from a
file? That's an excellent observation,  
and there is a better way of doing it. The _named slots_ in the target page can be extracted out into their own
respective files, and then imported explicitly
by the target page. The important thing is to ensure that they have the _x-named_ attribute which matches a slot in the
template page.

> todos/fragment/todo-stats.xml

```xml

<x-nav id="todo-stats">
    <x-div x-eval="true">completed count: @{($ in todos if $.done == true).size()}</x-div>
    <x-button id="clear" x-show="todos.size() != 0" type="button">Clear All</x-button>
</x-nav>
```

> todos/fragment/todo-list.xml

```xml

<x-ul id="todo-list" class="listing" x-items="todos" x-key="id">
    <li>
        <label>
            <x-input type="checkbox" value="@{id}" checked="@{done}" onchange="toggle"/>
        </label>
        <x-span x-text="title">Read Book</x-span>
        <i class="remove" onclick="remove">x</i>
    </li>
</x-ul>
```

> todos/fragment/todo-form.xml

```xml

<x-form id="todo-form" hx-post="/htmx/" hx-trigger="submit" hx-target="#todo-list">
    <label>
        <input name="title"/>
    </label>
    <button type="submit">Add</button>
</x-form>
```

The final _target page_ will now look like this:

```xml

<x-layout x-template="/todos/todo-template.xml">

    <x-doctype x-doctype="&lt;!DOCTYPE html&gt;"/>

    <x-title x-named="title">Todo HTMX App</x-title>

    <x-script src="https://unpkg.com/htmx.org@1.9.10" defer="true"/>

    <x-include x-path="/todos/fragment/todo-stats.xml" x-named="todo-stats"/>

    <x-include x-path="/todos/fragment/todo-form.xml" x-named="todo-form"/>

    <x-include x-path="/todos/fragment/todo-list.xml" x-named="todo-list"/>
</x-layout>
```

The page fragments can now be reused in other places, and the previous _listItemTemplate_ will now read the necessary
file

> Updated template for each new todo

```java
public static Function<Map<String, Object>, String> listItemTemplate = (map) -> {
    String template = "/todos/fragment/todo-list.xml";
    return TemplateRuntime.eval(Objects.requireNonNull(TodosHtmxHandler.class.getResourceAsStream(template)), map).toString();
};
```

## Open items

1. The document still needs to retrieve all todos when th page first loads.
2. The template for each todo item should be loaded from a template fragment
