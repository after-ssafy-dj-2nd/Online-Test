import React, { useEffect, useState } from 'react';
import {timeDiff} from '../../../util/time'


const StartExamButton = (props) => {
  const propsDiffTime = props.diffTime
  const [diffTime, setDiffTime ] = useState(propsDiffTime)
  
  useEffect(() => {
    if (diffTime === 9999999) {
      setDiffTime(props.diffTime)
    }
    else {
      const timer = setInterval(() => {
        setDiffTime(diffTime => diffTime - 1)
      }, 1000)
      return () => clearInterval(timer)
    }
  },[props.diffTime, diffTime])
  return (
    <div>
      <button>{timeDiff(diffTime)}</button>
    </div>
  );
};

export default StartExamButton;