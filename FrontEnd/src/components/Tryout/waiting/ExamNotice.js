import React from 'react';
import './ExamWaiting.css'
const ExamNotice = () => {
  return (
    <div className="examnotice">
      <ol>
        <li>테스트는 순서와 상관없이 문제를 풀 수 있습니다.</li>
        <li>시험이 시작되면 멈출수 없으며, 주어진 시간 내로 문제를 풀어야 합니다.</li>
        <li>테스트 중에 브라우저가 종료되어도 다시 테스트를 진행할 수 있습니다. 이때 임시저장 버튼을 누른 시점으로 복원됩니다.</li>
      </ol>
    </div>
  );
};

export default ExamNotice;