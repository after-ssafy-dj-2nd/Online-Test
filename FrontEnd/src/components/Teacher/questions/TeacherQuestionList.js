import React, { useState, useEffect } from 'react'
import {fetchQuestions ,deleteQuestion} from '../../../api/modules/questions'
import Question from '../../Question'

import { withRouter } from 'react-router-dom';

let DEFAULT = []

const TeacherQuestionList = ({history}) => {
  const [questions, setQuestions] = useState([])
  const [showQuestionId, setShowQuestionId] = useState(null)
  useEffect(() => {
    const fetchData = async () => {
      try {
        const { data } = await fetchQuestions();
        DEFAULT = data.question
        setQuestions(DEFAULT)
      } catch {
        setQuestions(DEFAULT)
        return
      }
    };
    fetchData();
  }, []);

  const filterQuestion = (value)=>{
    setQuestions(DEFAULT.filter(question => question.content.includes(value)))
  }
  const delQuestion = async (question) => {
    const confirm = window.confirm(`${question.content} 문제를 지우시겠습니까?`)
    if (confirm) {
        DEFAULT = DEFAULT.filter(q => q.id !== question.id)
        setQuestions(DEFAULT)
        await deleteQuestion(question.id)
    }
  }
  return (
    <div>
      <input type="text" onChange={e=>filterQuestion(e.target.value)}/>
      {questions.map(question=>(
        <div className="question" key={question.id}>
          <Question question={question}></Question>
          <button onClick={() => delQuestion(question)}>X</button>
        </div>
        )
      )}
      <button className="btn btn--primary" onClick={()=>history.push('/teacher/questions/add/')}>문제 만들러 가기</button>
    </div>
  )
}

export default withRouter(TeacherQuestionList)
