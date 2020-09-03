import React from 'react';
import { Link } from 'react-router-dom';

const notFoundPageStyle = {
  margin: '1rem auto',
  textAlign: 'center',
  lineHeight: 1.5
}

const exclamationMarkStyle = {
  color: 'var(--red)',
  fontSize: '5rem',
  marginBottom: '1rem'
}

const NotFound = () => {
  return (
    <div style={notFoundPageStyle}>
      <i className="fas fa-exclamation-triangle" style={exclamationMarkStyle} />
      <h3><b>이 페이지는 없는 페이지 입니다.</b></h3>
      <Link to="/">메인으로</Link>
    </div>
  );
};

export default NotFound;