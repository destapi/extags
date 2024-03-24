let currentListener = undefined;

export function createSignal(initialValue) {
	let value = initialValue;

	// a set of callback functions, from createEffect
	const subscribers = new Set();

	const read = () => {
		if (currentListener !== undefined) {
			// before returning, track the current listener
			subscribers.add(currentListener);
		}
		return value;
	};
	const write = (newValue) => {
		value = newValue;
		// after setting the value, run any subscriber, aka effect, functions
		subscribers.forEach((fn) => fn());
	};

	return [read, write];
}

export function createEffect(callback) {
	currentListener = callback;
	callback();
	currentListener = undefined;
}
