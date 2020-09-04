import React from 'react';
import { withRouter } from 'react-router-dom';
import Signup from '../components/Login/Signup';
import SelectSignupType from '../components/Login/SelectSignupType';

const SignupPage = ({ location }) => {
  const query = new URLSearchParams(location.search);
  const signupType = query.get('type');

  return (
    signupType ? <Signup signupType={signupType} /> : <SelectSignupType />
  );
};

export default withRouter(SignupPage);