import React, { useState, useEffect } from 'react'
import {fetchQuestions, } from '../../../api/modules/questions'
import Question from '../../Question'

const DEFAULT = []
const TeacherExamDetail = (props) => {
  const selected = props.selected
  const onSubmit = (e) => {
    e.preventDefault();
    props.putQuestions(selectedItems)
  }
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
  const onChange = id => {
    if (selectedItems.some(item=> item.question_id === id)) {
      setSelectedItems(selectedItems.filter(item=> item.question_id !== id));
    } else {
      setSelectedItems(selectedItems.concat({question_id:id,score:0}));
    }
  };
  const filterQuestion = (value)=>{
    setQuestions(DEFAULT.filter(question => question.content.includes(value)))
  }

  const checkItem = (id) => {
    return selectedItems.some(item => item.question_id === id)
  }
  const changeScore = (id,e) => {
    setSelectedItems(selectedItems.map(item => {
      if (item.question_id===id){
        item.score = e.target.value
      }
      return item
    })
    )
  }
  return (
    <>
      <hr/>
      <div>
        {'총점 : ' + selectedItems.reduce((prev,cur)=> {
          if (cur.score) {
            prev += parseInt(cur.score)
          }
          return prev
        },0) + '점'}
      </div>
      <span>선택된 문제</span>
      <div className="questions-box">
        {selectedItems.map((item,i)=> 
          <button onClick={()=>setShowQuestionId(item.question_id)} key={i}>
            <p>{i+1} 번 문제</p>
          </button>
        )}
      </div>
      <div className="selected-question">
        {questions.filter((question) => question.id === showQuestionId)
          .map((question)=>
            <div key={question.id}>
              <Question
                show={true}
                question={question}>
              </Question>
              <input type="number" value={selectedItems.filter(e=>e.question_id===question.id)[0].score} onChange={(e)=> changeScore(question.id,e)}/>
              <span>점</span>
            </div>
        )}
      </div>
      <hr/>
      <span>내가 만든 문제</span>
      <input type="text" onChange={e=>filterQuestion(e.target.value)}/>
      <form onSubmit={e=>onSubmit(e)}>
        {questions.map(question=>(
          <div className="choice-question" key={question.id}>
            <i onClick={()=> onChange(question.id)}
              className={"far " + (checkItem(question.id) ? "fa-check-square" : "fa-square")}
            >
            </i>
            <Question question={question} />
            {checkItem(question.id) ? 
              <>
                <input type="number"
                  value={selectedItems.filter(e=>e.question_id===question.id)[0].score} 
                  onChange={(e)=> changeScore(question.id,e)}
                />
                <span>점</span>
              </>
              : ''}
          </div>
          )
        )}
        <button type="submit">문제 등록 하기  </button>
      </form>
    </>
  )
}

export default TeacherExamDetail
