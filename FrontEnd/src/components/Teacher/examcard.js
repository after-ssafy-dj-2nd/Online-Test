import React from 'react'
import {timeDiff, timeToString, diffSecond} from '../../util/time'
const Exam = (props) => {
  const {exam} = props
  const examStatus = () => {
    const startSec = diffSecond(Date(),exam.startTime)
    const endSec = diffSecond(Date(),exam.endTime)
    if (startSec <0 && 0 < endSec){
      return `진행중 ${timeDiff(endSec)}남음`
    } else if (startSec>0){
      return `${timeDiff(startSec)} 남음`
    } else {
      return `종료`
    }
  }
  const lockIconClass = () =>{
    return exam.isOpen ? 'fas fa-lock-open' : 'fas fa-lock'
  }

  return (
    <div className="exam--card">
      <div>
        <i className={lockIconClass()}></i>
      </div>
      <div>
        {exam.title}
      </div>
      <div>
        {exam.participants} 명
      </div>
      <div>
        {timeToString(exam.startTime)} ~ {timeToString(exam.endTime).slice(11,)}
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

export default Exam
