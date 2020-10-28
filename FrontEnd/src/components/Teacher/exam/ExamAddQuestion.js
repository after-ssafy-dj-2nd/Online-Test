import React, { useState, useEffect } from 'react'
import {fetchQuestions} from '../../../api/modules/questions'
import Question from '../../Question'

const DEFAULT = []
const TeacherExamDetail = (props) => {
  const selected = props.selected
  const [questions, setQuestions] = useState([])
  const [selectedItems, setSelectedItems] = useState(props.selected)
  const [showQuestionId, setShowQuestionId] = useState(null)
  useEffect(() => {
    const fetchData = async () => {
      try {
        const { data } = await fetchQuestions();
        const DEFAULT = data.question
        setQuestions(DEFAULT)
      } catch {
        setQuestions(DEFAULT)
        return
      }
    };
    fetchData();
  }, []);
  useEffect(()=> {
    setSelectedItems(selected)
  },[selected])
  const onSubmit = (e) => {
    e.preventDefault()
  }
  const onChange = id => {
    if (selectedItems.includes(id)) {
      setSelectedItems(selectedItems.filter(item=> item !== id));
    } else {
      setSelectedItems(selectedItems.concat(id));
    }
  };
  const filterQuestion = (value)=>{
    setQuestions(DEFAULT.filter(question => question.content.includes(value)))
  }

  const checkItem = (id) => {
    return selectedItems.includes(id)
  }
  return (
    <>
      <hr/>
      <span>선택된 문제</span>
      <div className="questions-box">
        {selectedItems.map((questionId,i)=> 
          <button onClick={()=>setShowQuestionId(questionId)} key={i}>
            <p>{i+1} 번 문제</p>
          </button>
        )}
      </div>
      <div className="selected-question">
        {questions.filter(question => question.id === showQuestionId)
          .map((question,id)=>
            <Question
              show={true}
              key={id}
              question={question}>
            </Question>
        )}
      </div>
      <hr/>
      <span>내가 만든 문제</span>
      <input type="text" onChange={e=>filterQuestion(e.target.value)}/>
      <form onSubmit={e=>onSubmit(e)}>
        {questions.map(question=>(
          <div key={question.id}>
            <i onClick={()=> onChange(question.id)}
              className={"far " + (checkItem(question.id) ? "fa-check-square" : "fa-square")}
            >
            </i>
            <Question question={question}></Question>
          </div>
          )
        )}
        <button type="submit">문제 등록 하기  </button>
      </form>
    </>
  )
}

export default TeacherExamDetail
