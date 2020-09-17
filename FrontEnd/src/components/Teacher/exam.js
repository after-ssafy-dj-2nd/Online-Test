import React from 'react'
import {timeDiff, timeToString, diffSecond} from '../../util/time'
const Examcard = (props) => {
  const {exam} = props
  const examStatus = () => {
    const startSec = diffSecond(Date(),exam.startTime)
    const endSec = diffSecond(Date(),exam.endTime)
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

  return (
    <div className="exam--row">
      <div>
        <i className={lockIconClass()}></i>
      </div>
      <div>
        {exam.title}
      </div>
      <div>
        {exam.participants}
      </div>
      <div>
        <p>{timeToString(exam.startTime)}</p>
        <p>{'~ ' + timeToString(exam.endTime)}</p>
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
