document.addEventListener("DOMContentLoaded", async () => {
    const markup = await fetch("http://localhost:8080/htmx/")
        .then(res => res.text());
    console.log(markup)
    const listing = document.getElementById("todo-list");
    const fragment = document.createRange().createContextualFragment(markup);
    // const element = fragment.firstElementChild;
    listing.append(fragment)
});