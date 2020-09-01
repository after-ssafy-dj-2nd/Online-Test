import React, { useState, useCallback } from 'react';
import './Login.css';

const Login = () => {
  const [userName, setUserName] = useState('');
  
  const onClick = useCallback(() => {
    setUserName('new name');
  }, []);

  return (
    <div>
      {userName}
      <button onClick={onClick}>Show</button>
    </div>
  );
};

export default Login;