import React, { useState, useEffect } from 'react'
import {timeToString} from '../../../util/time'
import ExamAddQuestion from './ExamAddQuestion'
import ExamAddStudent from './ExamAddStudent'
import {fetchExam} from '../../../api/modules/exams'

const TeacherExamDetail = ({match}) => {
  const examId = match.params.id
  const [exam, setExam] = useState(
    { name : '',
      starttime : '',
      endtime : '',
      questions:[]
    }
  )
  useEffect(()=>{
    const fetchData = async ()=> {
      try {
        const { data } = await fetchExam(examId);
        setExam(data)
      } catch (e){
        console.log(e)
        return
      }
    };
    fetchData()
  },[])
  const [addType, setAddType] = useState('question')
  return (
    <>
      <div>
        <div>{exam.name}</div>
        <div>시험 시작 시간 : {timeToString(exam.starttime)}</div>
        <div>시험 종료 시간 : {timeToString(exam.endtime)}</div>
        <div>정원 : {exam.participants}</div>
      </div>
      <div>
        <div>
          <div className={"btn btn-large" + ((addType==="student") ? '' : ' bg-orangered')} onClick={()=>setAddType('question')}>문제</div>
          <div className={"btn btn-large" + ((addType==="question") ? '' : ' bg-orangered')} onClick={()=>setAddType('student')}>학생</div>
        </div>
        <div>
        <div className={(addType==="question") ? ' ' : 'hide'} >
            <ExamAddQuestion selected={exam.questions.map(question=>question.id)}/>
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
