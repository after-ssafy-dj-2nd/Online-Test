import React from 'react';
import { Link } from 'react-router-dom';
import './SelectSignupType.css';

const SelectSignupType = () => {
  return (
    <div className="select-signup-wrapper">
      <h2>가입할 계정의 유형을 선택해주세요.</h2>
      <div className="signup-button-group">
        <Link to="/signup?type=student">
          <i className="fas fa-user" />
          <p><span>학생 계정</span> <span>회원가입</span></p>
        </Link>
        <Link to="/signup?type=teacher">
          <i className="fas fa-chalkboard-teacher" />
          <p><span>선생님 계정</span> <span>회원가입</span></p>
        </Link>
      </div>
    </div>
  );
};

export default SelectSignupType;