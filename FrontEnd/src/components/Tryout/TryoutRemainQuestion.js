import React from 'react';
import './TryoutRemainQuestion.css';

const TryoutRemainQuestion = ({ myAnswer }) => {
  return (
    <div className="tryout-sidebar-box-style">
      <div className="tryout-remain-question-title">
        <i className="fas fa-question" /> 해결한 문제 수
      </div>
      {myAnswer.filter(answer => answer).length} / {myAnswer.length}
    </div>
  );
};

export default TryoutRemainQuestion;