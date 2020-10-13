import React from 'react';

const ExamWaitingSection = (props) => {
  return (
    <div className="examwaiting-section">
      <p className="title gray">{props.title}</p>
      {props.children}
    </div>
  );
};

export default ExamWaitingSection;