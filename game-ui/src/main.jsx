import React from 'react'
import ReactDOM from 'react-dom/client'
import { Quiz } from './components/Quiz'
import "./assets/index.css"
import { QuizProvider } from './store/quiz'

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <QuizProvider>
      <Quiz />
    </QuizProvider>
  </React.StrictMode>,
)
