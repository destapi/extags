import PropTypes from 'prop-types';

const letters = ['A', 'B', 'C', 'D'];

export const Answer = ({ answerText, onSelectAnswer, index, currentAnswer, correctAnswer }) => {

    const isCorrectAnswer = currentAnswer && answerText === correctAnswer;
    const isWrongAnswer = currentAnswer == answerText && currentAnswer !== correctAnswer;
    const correctAnswerClass = isCorrectAnswer ? 'correct-answer' : '';
    const wrongAnswerClass = isWrongAnswer ? 'wrong-answer' : '';
    const disabledClass = currentAnswer ? 'disabled-answer' : '';

    return (
        <div className={`answer ${correctAnswerClass} ${wrongAnswerClass} ${disabledClass}`} onClick={() => onSelectAnswer(answerText)}>
            <div className="answer-letter">{letters[index]}</div>
            <div className="answer-text">{answerText}</div>
        </div>
    )
}

Answer.propTypes = {
    index: PropTypes.number.isRequired,
    answerText: PropTypes.string.isRequired,
    onSelectAnswer: PropTypes.func.isRequired,
    currentAnswer: PropTypes.string,
    correctAnswer: PropTypes.string,
}