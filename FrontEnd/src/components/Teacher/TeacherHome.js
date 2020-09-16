import React from 'react'
import Exam from './exam'
import Examcard from './examcard'

const EXAM_SAMPLE = [
    {
      title: '1번 시험',
      startTime: '2020-09-15T13:00',
      endTime:'2020-09-15T15:00',
      isOpen: true,
      participants : 10,
      password : ''
    },
    {
      title: '2번 시험',
      startTime: '2020-09-16T00:00',
      endTime:'2020-09-17T15:00',
      isOpen: false,
      participants : 20,
      password : 'asdf'
    },
    {
      title: '3번 시험',
      startTime: '2020-10-07T14:00',
      endTime:'2020-10-07T17:30',
      isOpen: false,
      participants : 12,
      password : 'asdf'
    },
  ]
const TeacherHome = () => {

  return (
    <div>
      <div className="exam--table text-center">
        <div className="exam--row">
          <div>공개</div>
          <div>제목</div>
          <div>인원수</div>
          <div>시험시간</div>
          <div>진행시간</div>
          <div>상태</div>
        </div>
        {EXAM_SAMPLE.map((exam, key) => <Exam key={key} exam={exam}></Exam>)}
      </div>
      <div className="exam--cards">
        {EXAM_SAMPLE.map((exam, key) => <Examcard key={key} exam={exam}></Examcard>)}
      </div>
    </div>
  )
}

export default TeacherHome
