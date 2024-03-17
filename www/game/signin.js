const form = document.querySelector("form")
const password = document.querySelector("nord-input[type='password']")
const email = document.querySelector("nord-input[type='email']")

// When the submit button is clicked do a fake validation
form.addEventListener("submit", e => {
    const errorMsg = "This field is required"

    // Prevent form submission if user input is missing
    if (!password.value || !email.value) {
        e.preventDefault()
        e.stopPropagation()
    }

    // Show an error, announce it to the screen reader,
    // and set focus to this input.
    if (!password.value) {
        password.error = errorMsg
        password.focus()
    }
    if (!email.value) {
        email.error = errorMsg
        email.focus()
    }
})

function clearError(e) {
    e.target.error = null
}

// Finally, for demo purposes, let’s add change listeners for the
// form fields that remove the error when something is inputted
email.addEventListener("input", clearError)
password.addEventListener("input", clearError)