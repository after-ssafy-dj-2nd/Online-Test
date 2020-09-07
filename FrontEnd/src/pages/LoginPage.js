import React from 'react';
import { withRouter } from 'react-router-dom';
import Login from '../components/Login/Login';
import FindPassword from '../components/Login/FindPassword';

const LoginPage = ({ location }) => {
  const { pathname } = location;

  return (
    pathname === '/login' ? <Login /> : <FindPassword />
  );
};

export default withRouter(LoginPage);