import React from 'react'
import { withRouter } from 'react-router-dom';
import {timeDiff, timeToString, diffSecond} from '../../../util/time'

const Examcard = (props) => {
  const {exam, history, deleteExam} = props
  const examStatus = () => {
    const startSec = diffSecond(Date(),exam.starttime)
    const endSec = diffSecond(Date(),exam.endtime)
    if (startSec <0 && 0 < endSec){
      return (
        <>
          <p>진행 중</p>
          <p>{timeDiff(endSec)} 남음</p>
        </>
      )
    } else if (startSec>0){
      return (
        <>
          <p>대기 중</p>
          <p>{timeDiff(startSec)} 남음</p>
        </>
      )
    } else {
      return `종료`
    }
  }
  const lockIconClass = () =>{
    return exam.isOpen ? 'fas fa-lock-open' : 'fas fa-lock'
  }
  const goExamDetail = () => {
    history.push( `/teacher/exam/${exam.id}`);
  }

  return (
    <div className="exam--row">
      <div>
        <i className={lockIconClass()}></i>
      </div>
      <div onClick={goExamDetail}>
        {exam.name}
      </div>
      <div>
        {exam.questions.length}
      </div>
      <div>
        <p>{timeToString(exam.starttime)}</p>
        <p>{'~ ' + timeToString(exam.endtime)}</p>
      </div>
      <div>
        {timeDiff(diffSecond(exam.starttime,exam.endtime))}
      </div>
      <div>
        {examStatus()}
      </div>
      <div onClick={deleteExam}>
        <i className="far fa-trash-alt"></i>
      </div>
    </div>
  )
}

export default withRouter(Examcard)