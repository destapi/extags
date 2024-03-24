import { useContext } from "react";
import { Answer } from "./Answer"
import { QuizContext } from "../store/quiz";

export const Question = () => {

    const { state: { currentQuestionIndex, questions, answers, currentAnswer, }, dispatch } = useContext(QuizContext);
    const currentQuestion = questions[currentQuestionIndex];

    return (
        <div>
            <div className="question">{currentQuestion.question}</div>
            <div className="answers">
                {answers.map((answer, i) => <Answer 
                key={i} 
                answerText={answer} 
                currentAnswer={currentAnswer} 
                correctAnswer={currentQuestion.correctAnswer}
                index={i} onSelectAnswer={(answerText) => dispatch({type: 'SELECT_ANSWER', payload: answerText})} /> )}
            </div>
        </div>
    )
}