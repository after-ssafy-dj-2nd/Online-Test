import React from 'react';
import { Redirect, withRouter } from 'react-router-dom';
import Signup from '../components/Login/Signup';
import SelectSignupType from '../components/Login/SelectSignupType';
import { useSelector } from 'react-redux';

const SignupPage = ({ location }) => {
  const query = new URLSearchParams(location.search);
  const signupType = query.get('type');
  const { loginStatus } = useSelector(state => state.users);

  return (
    <>
      {!loginStatus
        ? signupType ? <Signup signupType={signupType} /> : <SelectSignupType />
        : <Redirect to="/main" />
      }
    </>
  );
};

export default withRouter(SignupPage);