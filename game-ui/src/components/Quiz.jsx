import { useContext, useEffect } from "react";
import { Question } from "./Question";
import { QuizContext } from "../store/quiz";

export const Quiz = () => {

    const triviaUrl = "https://opentdb.com/api.php?amount=10&encode=url3986";
    const { state: { currentQuestionIndex, questions, showResults, correctAnswersCount }, dispatch } = useContext(QuizContext);

    useEffect(() => {
        if (questions.length == 0) {
            fetch(triviaUrl).then(res => res.json()).then(data => {
                dispatch({ type: 'LOADED_QUESTIONS', payload: data.results })
            })
        }
    })

    return (
        <div className="quiz">
            {showResults && (
                <div className="results">
                    <div className="congratulations">Congrats</div>
                    <div className="results-info">
                        <div> You have completed the quiz</div>
                        <div>You got {correctAnswersCount} of {questions.length}</div>
                    </div>
                    <div className="next-button" onClick={() => dispatch({ type: 'RESTART' })}>Restart</div>
                </div>
            )}
            {!showResults && questions.length > 0 && (
                <div>
                    <div className="score">Question {currentQuestionIndex + 1}/{questions.length}</div>
                    <Question />
                    <div className="next-button" onClick={() => dispatch({ type: 'NEXT_QUESTION', })}>Next Question</div>
                </div>
            )}
        </div>
    )
}