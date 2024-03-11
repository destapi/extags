const appName = "todos-app";

document.addEventListener("DOMContentLoaded",e => {
    const storeStr = localStorage.getItem(appName) || "[]";
    const store = JSON.parse(storeStr);
    const event = new CustomEvent("refresh", { detail: store });
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
clearBtn.addEventListener('click', e => {
    localStorage.removeItem(appName);
    const event = new CustomEvent("refresh", { detail: [] });
    listing.dispatchEvent(event);
    stats.dispatchEvent(event);
    clearBtn.dispatchEvent(event);
})

function randomIntFromInterval(min, max) { // min and max included
    return Math.floor(Math.random() * (max - min + 1) + min)
}

function edit(e){
    console.log(e.target.value)
}

function add(e){
    e.preventDefault();
    const title = e.target.title.value;
    e.target.title.value = "";

    const newTodo = ({id: randomIntFromInterval(1, 100), title, done: false})
    const storeStr = localStorage.getItem(appName) || "[]";
    const store = JSON.parse(storeStr);
    store.push(newTodo);
    localStorage.setItem(appName, JSON.stringify(store));

    //notify listing
    const event = new CustomEvent("refresh", { detail: store });
    listing.dispatchEvent(event);
    stats.dispatchEvent(event);
    clearBtn.dispatchEvent(event);
}

function refreshTodos({detail: todos}){
    while(listing.firstElementChild) {
        listing.firstElementChild.remove();
    }
    for(let todo of todos){
        const template = `
        <li data-x-id="${todo.id}">
            <label><input type="checkbox" ${todo.done? 'checked' : ''} /> </label>
            <span>${todo.title}</span>
        </li>`;

        const fragment = document.createRange().createContextualFragment(template);
        listing.appendChild(fragment.firstElementChild);
    }
}

function refreshStats({detail: todos}){
    stats.querySelector("div").innerHTML = `completed count: ${todos.length}`
}

function refreshClear({detail: todos}){
    clearBtn.dataset['xShow'] = String(todos.length > 0);
}