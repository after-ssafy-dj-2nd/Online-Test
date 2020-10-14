import React from 'react';
import { Redirect, withRouter } from 'react-router-dom';
import Login from '../components/Login/Login';
import FindPassword from '../components/Login/FindPassword';
import { useSelector } from 'react-redux';

const LoginPage = ({ location }) => {
  const { pathname } = location;
  const { loginStatus } = useSelector(state => state.users);

  return (
    <>
      {!loginStatus
        ? pathname === '/login' ? <Login /> : <FindPassword />
        : <Redirect to="/main" />
      }
    </>
  );
};

export default withRouter(LoginPage);