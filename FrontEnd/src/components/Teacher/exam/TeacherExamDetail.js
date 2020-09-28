import React, { useState, useEffect, useCallback } from 'react'
import { useLocation } from "react-router-dom";
import {timeToString} from '../../../util/time'
import {fetchQuestions} from '../../../api/modules/questions'
import Question from '../../Question'

const DEFAULT = [
  {
    "id": 13,
    "content": "다음중 맞는 말을 골라주세요",
    "description": "밥 먹었니",
    "commentary": "",
    "type": true,
    "writer_id": 2,
    "score": 0,
    "examples": []
  },
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
    "id": 16,
    "content": "문제 제목",
    "description": "해설",
    "commentary": "문제 내용",
    "type": true,
    "writer_id": 2,
    "score": 0,
    "examples": []
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
const TeacherExamDetail = (props) => {
  const location = useLocation();
  const [questions, setQuestions] = useState([])
  const [selectedItems, setSelectedItems ] = useState([])
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
  const exam = location.state.exam
  const onSubmit = (e) => {
    e.preventDefault()
    console.log(selectedItems)
  }
  const onChange = id => {
    if (selectedItems.includes(id)) {
      setSelectedItems(selectedItems.filter(item => item !== id));
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
    <div>
      <div>
        <div>{exam.title}</div>
        <div>시험 시작 시간 : {timeToString(exam.startTime)}</div>
        <div>시험 종료 시간 : {timeToString(exam.endTime)}</div>
        <div>정원 : {exam.participants}</div>
      </div>
      <div>
        <hr></hr>
        문제 등록하기
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
      </div>
    </div>
  )
}

export default TeacherExamDetail
