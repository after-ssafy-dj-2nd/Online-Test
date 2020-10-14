import React, { useState, useEffect } from 'react'
import { useLocation } from "react-router-dom";
import {timeToString} from '../../../util/time'
import ExamAddQuestion from './ExamAddQuestion'
import ExamAddStudent from './ExamAddStudent'

const TeacherExamDetail = (props) => {
  const location = useLocation();
  const exam = location.state.exam
  const [addType, setAddType] = useState('question')
  return (
    <>
      <div>
        <div>{exam.title}</div>
        <div>시험 시작 시간 : {timeToString(exam.startTime)}</div>
        <div>시험 종료 시간 : {timeToString(exam.endTime)}</div>
        <div>정원 : {exam.participants}</div>
      </div>
      <div>
        <div>
          <div className={"btn btn-large" + ((addType==="student") ? '' : ' bg-orangered')} onClick={()=>setAddType('question')}>문제</div>
          <div className={"btn btn-large" + ((addType==="question") ? '' : ' bg-orangered')} onClick={()=>setAddType('student')}>학생</div>
        </div>
        <div>
        <div className={(addType==="question") ? ' ' : 'hide'} >
            <ExamAddQuestion />
          </div>
          <div className={(addType==="student") ? ' ' : 'hide'} >
            <ExamAddStudent />
          </div>
        </div>
      </div>
    </>
  )
}

export default TeacherExamDetail
