import React from 'react';
import './IntroDefault.css';
import './Welcome.css';
const Welcome = () => {
  return (
    <div className="intro-content">
      <div className="welcome-wrap">
        <div className="welcome-contents">
          <div className="project-name">
            <h1>프로젝트 이름 및 간단한 소개</h1>
            <button>시작하기</button>
          </div>
          <div className="welcome-image">이미지 넣을 자리</div>
        </div>
        <div className="welcome-button">
          <button className="down-button">Down</button>
        </div>
      </div>
  </div>
  );
};

export default Welcome;