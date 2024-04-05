import { createSignal, createEffect } from "./signal.js";

const [count, setCount] = createSignal(0);

const button = document.createElement('button');
createEffect(() => {
	button.innerText = count();
});

button.addEventListener('click', () => {
	setCount(count() + 1);
});

document.body.append(button);