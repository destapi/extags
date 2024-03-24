export const shuffleAnswers = question => {
    const unshuffledAnswers = [
        question.correctAnswer,
        ...question.incorrectAnswers
    ]

    return unshuffledAnswers.map(unshuffledAnswer => ({
        sort: Math.random(),
        value: unshuffledAnswer,
    }))
        .sort((a, b) => a.sort - b.sort)
        .map(a => a.value);
}

export const normalizeTriviaApiData = apiQuestions => {
    return apiQuestions.map(apiQuestion => {
        const incorrectAnswers = apiQuestion.incorrect_answers.map(ia => decodeURIComponent(ia))
        return ({
            correctAnswer: decodeURIComponent(apiQuestion.correct_answer),
            question: decodeURIComponent(apiQuestion.question),
            incorrectAnswers,
        })
    })
}