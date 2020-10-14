import React, { useState, useEffect } from 'react'
import { useLocation } from "react-router-dom";
import {timeToString} from '../../../util/time'
import {fetchQuestions} from '../../../api/modules/questions'
import Question from '../../Question'

const DEFAULT = [
  {
    "id": 15,
    "content": "test_content",
    "description": "test_description",
    "commentary": "test_comment",
    "type": true,
    "writer_id": 2,
    "score": 0,
    "examples": [
      {
        "id": 19,
        "question_id": 15,
        "content": "test_example",
        "correct": true
      },
      {
        "id": 20,
        "question_id": 15,
        "content": "test_example2",
        "correct": false
      },
      {
        "id": 21,
        "question_id": 15,
        "content": "test_example3",
        "correct": false
      }
    ]
  },
  {
    "id": 17,
    "content": "문제 제목",
    "description": "해설",
    "commentary": "문제 내용",
    "type": true,
    "writer_id": 2,
    "score": 0,
    "examples": [
      {
        "id": 27,
        "question_id": 17,
        "content": "보기1",
        "correct": true
      },
      {
        "id": 28,
        "question_id": 17,
        "content": "보기2",
        "correct": false
      },
      {
        "id": 29,
        "question_id": 17,
        "content": "보기3",
        "correct": false
      },
      {
        "id": 30,
        "question_id": 17,
        "content": "보기4",
        "correct": false
      }
    ]
  },
  {
    "id": 18,
    "content": "주관식1",
    "description": "주관식1",
    "commentary": "주관식입니다",
    "type": false,
    "writer_id": 2,
    "score": 0,
    "examples": [
      {
        "id": 31,
        "question_id": 18,
        "content": "주관식정답1",
        "correct": true
      },
      {
        "id": 32,
        "question_id": 18,
        "content": "주관식정답2",
        "correct": true
      }
    ]
  }
]
const TeacherExamDetail = () => {
  const [questions, setQuestions] = useState([])
  const [selectedItems, setSelectedItems ] = useState([])
  const [showQuestionId, setShowQuestionId] = useState(null)
  useEffect(() => {
    const fetchData = async () => {
      try {
        // const { data } = await fetchQuestions();
        // setQuestions(data);
        // const DEFAULT = data
        setQuestions(DEFAULT)
      } catch {
        setQuestions(DEFAULT)
        return
      }
    };
    fetchData();
  }, []);
  const onSubmit = (e) => {
    e.preventDefault()
  }
  const onChange = question => {
    if (selectedItems.includes(question)) {
      setSelectedItems(selectedItems.filter(item => item !== question));
    } else {
      setSelectedItems(selectedItems.concat(question));
    }
  };
  const filterQuestion = (value)=>{
    setQuestions(DEFAULT.filter(question => question.content.includes(value)))
  }

  const checkItem = (question) => {
    return selectedItems.includes(question)
  }
  return (
    <>
      <hr/>
      <span>선택된 문제</span>
      <div className="questions-box">
        {selectedItems.map((question,id)=> 
          <button onClick={()=>setShowQuestionId(question.id)} key={id}>
            <p>{id+1}</p>
            <p>{question.content}</p>
          </button>
        )}
      </div>
      <div className="selected-question">
        {selectedItems.filter(question => question.id === showQuestionId)
          .map((question,id)=>
            <Question key={id}
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
            <i onClick={()=> onChange(question)}
              className={"far " + (checkItem(question) ? "fa-check-square" : "fa-square")}
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
