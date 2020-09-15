import React from 'react';

const ExamForm = ({ match }) => {
  const examId = match.params.examId;

  return (
    <div>
      { examId }번의 시험 고유 번호를 가진 시험 응시 페이지
    </div>
  );
};

export default ExamForm;