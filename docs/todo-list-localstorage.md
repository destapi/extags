## EzTag - TodoList with localStorage

This is an example of how a todolist can be put together using _eztags_. This is a very basic version of the app

> The layout page (/todos/todo-template.xml)

```html
<html lang="en">
    <head>
        <meta charset="UTF-8"/>
        <x-title x-slot="title">Todos</x-title>
        <link rel="stylesheet" href="css/style.css" type="text/css" />
    </head>
    <body>
        <main id="root">
            <x-main x-slot="todo-form"/>

            <x-main x-slot="todo-list"/>

            <x-main x-slot="todo-stats"/>
        </main>

        <footer>&amp;copy; 2024 EzTags</footer>
        <script src="js/todos.js"/>
    </body>
</html>
```

The layout:
1. has no _&lt;!doctype&gt;_ because the template markup must be _valid XML_. This tag can however be optionally injected, as will be seen later.
2. has a slot for a _title_ which can also be optionally injected, or else the title in the layout template would be used
3. has three named slots _(todo-form, todo-list, todo-stats)_ which just like the title slot can optionally be injected, or else, the resulting
markup would be empty tags
4. contains the stylesheet and script tags to be used in the generated page

> The app page (/todos/todo-app.xml)

```html
<x-layout x-template="/todos/todo-template.xml">

    <x-doctype x-doctype="&lt;!DOCTYPE html&gt;" />

    <x-title x-named="title">Todo App</x-title>

    <x-nav id="todo-stats" x-named="todo-stats">
        <x-div x-eval="true">completed count: @{todos.size()}</x-div>
        <x-button id="clear" x-show="todos.size() != 0" type="button">Clear All</x-button>
    </x-nav>

    <x-form id="todo-form" x-named="todo-form" onsubmit="add">
        <label><input name="title" /></label>
        <button type="submit">Add</button>
    </x-form>

    <x-ul id="todo-list" class="listing" x-named="todo-list" x-items="todos" x-key="id">
        <li>
            <label><x-input type="checkbox" value="@{id}" checked="@{done}" onchange="toggle"/> </label>
            <x-span x-text="title">Read Book</x-span>
            <i class="remove" onclick="remove">x</i>
        </li>
    </x-ul>
</x-layout>
```

The target page:
1. uses the _todo-template.xml_ layout to organize its components using the _named slots_ content
2. injects a _&lt;!doctype&gt;_ into the generated page
3. injects a _title_ to override the default in the generated page
4. injects three named parts (todo-form, todo-list, todo-stats) for the slots in the template page.
5. declares event handlers, and these should be available in the document's global scope before they can be attached successfully to the target elements

The glue that wires together the markup and the events generated on the page is provided manually using javascript.

> /todos/cs/todos.js

```js
const storageKey = "todos-app";

document.addEventListener("DOMContentLoaded", () => {
    const storeStr = localStorage.getItem(storageKey) || "[]";
    const store = JSON.parse(storeStr);
    const event = createRefreshEvent(store);
    listing.dispatchEvent(event);
    stats.dispatchEvent(event);
    clearBtn.dispatchEvent(event);
})

const listing = document.getElementById("todo-list");
listing.addEventListener("refresh", refreshTodos, false);

const stats = document.getElementById("todo-stats");
stats.addEventListener("refresh", refreshStats, false);

const form = document.getElementById("todo-form");
form.addEventListener("submit", add)

const clearBtn = document.getElementById("clear");
clearBtn.addEventListener("refresh", refreshClear, false);
clearBtn.addEventListener('click', () => {
    localStorage.removeItem(storageKey);
    const event = createRefreshEvent([]);
    refreshAllViews(event);
});

listing.querySelectorAll("input[type=checkbox]").forEach(input => {
    input.addEventListener('click', toggle)
});

function randomIntFromInterval(min, max) { // min and max included
    return Math.floor(Math.random() * (max - min + 1) + min)
}

function createRefreshEvent(store) {
    return new CustomEvent("refresh", {bubble: true, detail: store});
}

function refreshAllViews(event) {
    listing.dispatchEvent(event);
    stats.dispatchEvent(event);
    clearBtn.dispatchEvent(event);
}

function remove(id) {
    const storeStr = localStorage.getItem(storageKey);
    const store = JSON.parse(storeStr).filter(t => t.id !== id);
    localStorage.setItem(storageKey, JSON.stringify(store));
    const event = createRefreshEvent(store);
    refreshAllViews(event);
}

function toggle(id, done) {
    const storeStr = localStorage.getItem(storageKey);
    const store = JSON.parse(storeStr).map(t => {
        if (t.id === id) {
            return ({...t, done})
        }
        return t;
    });
    localStorage.setItem(storageKey, JSON.stringify(store));
    const event = createRefreshEvent(store);
    stats.dispatchEvent(event);
}

function add(e) {
    e.preventDefault();
    const title = e.target.title.value;
    e.target.title.value = "";

    const newTodo = ({id: randomIntFromInterval(1, 1000), title, done: false})
    const storeStr = localStorage.getItem(storageKey) || "[]";
    const store = JSON.parse(storeStr);
    store.push(newTodo);
    localStorage.setItem(storageKey, JSON.stringify(store));
    const event = createRefreshEvent(store);
    refreshAllViews(event);
}

function refreshTodos({detail: todos}) {
    while (listing.firstElementChild) {
        listing.firstElementChild.remove();
    }
    for (let todo of todos) {
        const template = `
        <li data-x-id="${todo.id}">
            <label><input type="checkbox" value="${todo.id}" ${todo.done ? 'checked' : ''} /> </label>
            <span>${todo.title}</span>
            <i class="remove" >x</i>
        </li>`;

        const fragment = document.createRange().createContextualFragment(template);
        const element = fragment.firstElementChild;
        element.querySelector("input[type=checkbox]").addEventListener('click', e => toggle(todo.id, e.target.checked))
        element.querySelector("i.remove").addEventListener('click', () => remove(todo.id))
        listing.appendChild(element);
    }
}

function refreshStats({detail: todos}) {
    stats.querySelector("div").innerHTML = `completed count: ${todos.filter(t => t.done).length}`
}

function refreshClear({detail: todos}) {
    clearBtn.dataset['xShow'] = String(todos.length > 0);
}
```

Add some more styling in the styles file to suit your taste.

> /todos/css/style.css

```css
[data-x-show=false] {
    display: none;
}

i.remove {
    cursor: pointer;
    display: inline-block;
    padding-left: 15px;
    color: darkred;
}
```

This should work as expected, with all the state being saved in the browser's localStorage and nothing happening on the server side.

Now, it would be easy to just convert the calls to save in localStorage into calls to submit to the backend using javascript's _fetch API_. 
But for this exercise, the goal is to _NOT_ use javascript, but instead rely on _HTMX_ entirely, to the furthest extent possible. That's the subject of the next section.
