import React from 'react';
import './TryoutQuestionButtons.css';

const TryoutQuestionButtons = ({ myAnswer, onChangeNowQuestionNumber }) => {
  return (
    <div className="tryout-question-buttons">
      {myAnswer.map((answer, i) => {
        return <div className="tryout-question-button" key={i} onClick={onChangeNowQuestionNumber}>{i + 1}</div>
      })}
    </div>
  );
};

export default TryoutQuestionButtons;