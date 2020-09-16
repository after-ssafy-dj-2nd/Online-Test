import React from 'react'
import {timeDiff, stringToTime, diffSecond} from '../../util/time'
const Examcard = (props) => {
  const {exam} = props
  const examStatus = () => {
    const start = diffSecond(Date(),exam.startTime)
    const end= diffSecond(Date(),exam.endTime)
    if (start<0 && 0 < end){
      return (
        <>
          <p>진행 중</p>
          <p>{timeDiff(end)} 남음</p>
        </>
      )
    } else if (start>0){
      return (
        <>
          <p>대기 중</p>
          <p>{timeDiff(start)} 남음</p>
        </>
      )
    } else {
      return `종료`
    }
  }
  const lockIcon = () =>{
    return exam.isOpen ? 'fas fa-lock-open' : 'fas fa-lock'
  }

  return (
    <div className="exam--row">
      <div>
        <i className={lockIcon()}></i>
      </div>
      <div>
        {exam.title}
      </div>
      <div>
        {exam.participants}
      </div>
      <div>
        <p>{stringToTime(exam.startTime)}</p>
        <p>{'~ ' + stringToTime(exam.endTime)}</p>
      </div>
      <div>
        {timeDiff(diffSecond(exam.startTime,exam.endTime))}
      </div>
      <div>
        {examStatus()}
      </div>
    </div>
  )
}

export default Examcard
