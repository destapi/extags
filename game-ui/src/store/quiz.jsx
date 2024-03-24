import { createContext, useReducer } from "react"
import PropTypes from 'prop-types';
// import questions from './data';
import { normalizeTriviaApiData, shuffleAnswers } from "./utils";

const initialState = {
    currentQuestionIndex: 0,
    questions: [],
    showResults: false,
    answers: [], //shuffleAnswers(questions[0]),
    currentAnswer: '',
    correctAnswersCount: 0,
}

const reducer = (state, action) => {
    switch (action.type) {
        case 'NEXT_QUESTION': {
            const showResults = state.currentQuestionIndex === state.questions.length - 1;
            const currentQuestionIndex = showResults ? state.currentQuestionIndex : state.currentQuestionIndex + 1;
            const answers = showResults ? [] : shuffleAnswers(state.questions[currentQuestionIndex])
            return ({
                ...state,
                currentQuestionIndex,
                showResults,
                answers,
                currentAnswer: '',
            })
        }
        case 'RESTART': {
            return initialState
        }
        case 'SELECT_ANSWER': {
            const correctAnswersCount = action.payload === state.questions[state.currentQuestionIndex].correctAnswer ?
                state.correctAnswersCount + 1 : state.correctAnswersCount
            return ({
                ...state,
                currentAnswer: action.payload,
                correctAnswersCount,
            });
        }
        case 'LOADED_QUESTIONS': {
            const normalizedQuestions = normalizeTriviaApiData(action.payload);
            return ({
                ...state,
                questions: normalizedQuestions,
                answers: shuffleAnswers(normalizedQuestions[0]),
            });
        }
        default: {
            return state
        }
    }
}

export const QuizContext = createContext();

export const QuizProvider = ({ children }) => {
    const [state, dispatch] = useReducer(reducer, initialState);

    return (
        <QuizContext.Provider value={{ state, dispatch }}>
            {children}
        </QuizContext.Provider>
    )
}

QuizProvider.propTypes = {
    children: PropTypes.object.isRequired
}