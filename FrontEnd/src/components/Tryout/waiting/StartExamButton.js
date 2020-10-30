import React, { useEffect, useState } from 'react';
import { useHistory } from 'react-router-dom';
import {timeDiff} from '../../../util/time'



const StartExamButton = (props) => {
  const propsDiffTime = props.diffTime
  const examId = props.examId
  const history = useHistory()
  const [diffTime, setDiffTime ] = useState(propsDiffTime)
  
  useEffect(() => {
    if (diffTime === 8639999) {
      setDiffTime(props.diffTime)
    }
    else if (diffTime > 0) {
      const timer = setInterval(() => {
        setDiffTime(diffTime => diffTime - 1)
      }, 1000)
      return () => clearInterval(timer)
    }
  },[props.diffTime, diffTime])
  
  const able = (remain) => {
    if (remain) return 'disabled'
    return '' 
  }
  const goNextPage = () => {
    if (!diffTime) history.push(`/tryout/${examId}/agreement`)
    else history.push("/main")
  }

  return (
    <div>
      <div className={'btn ' + able(diffTime)} onClick={goNextPage}>{diffTime ? timeDiff(diffTime):'테스트 시작'}</div>
    </div>
  );
};

export default StartExamButton;