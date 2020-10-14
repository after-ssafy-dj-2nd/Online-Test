import React from 'react';
import './TryoutQuestion.css';

const TryoutQuestion = ({ nowQuestion, nowQuestionNumber }) => {
  return (
    <>
      <div className="tryout-question-header">
        Q{nowQuestionNumber + 1}. {nowQuestion.content} ({nowQuestion.score}점)
      </div>
      <div className="tryout-question-examples">
        {nowQuestion.examples.map(example => {
          return <p key={example.id}>{example.content}</p>
        })}
      </div>
    </>
  );
};

export default TryoutQuestion;