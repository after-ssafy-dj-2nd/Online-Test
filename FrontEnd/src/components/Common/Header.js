import React from 'react';
import { Link } from 'react-router-dom';
import './Header.css';

const Header = () => {
  return (
    <header>
      <Link to="/">메인</Link>
      <Link to="/login">로그인</Link>
      <Link to="/signup">회원가입</Link>
    </header>
  );
};

export default Header;