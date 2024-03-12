const storageKey = "todos-app";

document.addEventListener("DOMContentLoaded", async () => {
    const store = await fetch("http://localhost:8080/todos/")
        .then(res => res.json());
    const event = createRefreshEvent(store);
    listing.dispatchEvent(event);
    stats.dispatchEvent(event);
    clearBtn.dispatchEvent(event);
});

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

async function remove(id) {
    const store = await fetch(`http://localhost:8080/todos/?id=${id}`, {
        method: "DELETE",
    })
        .then(res => res.json());
    const event = createRefreshEvent(store);
    refreshAllViews(event);
}

async function toggle(id, done) {
    const store = await fetch(`http://localhost:8080/todos/?id=${id}`, {
        method: "PUT",
    })
        .then(res => res.json());
    const event = createRefreshEvent(store);
    stats.dispatchEvent(event);
}

async function add(e) {
    e.preventDefault();
    const title = e.target.title.value;
    e.target.title.value = "";

    const store = await fetch("http://localhost:8080/todos/", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({title})
    })
        .then(res => res.json());
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