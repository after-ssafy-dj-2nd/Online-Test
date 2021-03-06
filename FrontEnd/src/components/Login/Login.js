import React, { useReducer, useState, useEffect, useRef, useCallback, memo } from 'react';
import { withRouter, Link } from 'react-router-dom';
import { storage } from '../../util/storage';
import { reducer } from '../../util/reducer';
import { login } from '../../api/modules/user';
import { saveUserInfo, changeLoginStatus } from '../../store/users';
import { useDispatch } from 'react-redux';
import './Login.css';

const Login = memo(({ history }) => {
  const [isSaveId, setIsSaveId] = useState(false);
  const checkBoxEl = useRef(null);

  const [state, dispatch] = useReducer(reducer, {
    email: '',
    password: ''
  });

  const { email, password } = state;


  useEffect(() => {
    const saveId = storage(localStorage).getItem('saveId');
    if (saveId) {
      checkBoxEl.current.checked = true;
      setIsSaveId(true);
      dispatch({
        id: 'id',
        value: saveId
      })
    } else {
      checkBoxEl.current.checked = false;
      setIsSaveId(false);
    }
  }, [checkBoxEl]);
  
  const onChange = e => {
    dispatch(e.target);
  };
  
  const onChangeIsSaveId = useCallback(e => {
    setIsSaveId(e.target.checked);
  }, []);


  const loginDispatch = useDispatch();
  const onLogin = useCallback(userInfo => loginDispatch(saveUserInfo(userInfo)), [loginDispatch]);
  const onChangeLoginStatus = useCallback(loginStatus => loginDispatch(changeLoginStatus(loginStatus)), [loginDispatch]);

  const onSubmit = async e => {
    e.preventDefault();

    // 아이디, 비밀번호 빈 값인지 확인하는 로직
    if (email.length === 0) {
      alert('이메일을 입력해주세요.');
      return
    }
    if (password.length === 0) {
      alert('비밀번호를 입력해주세요.');
      return
    }
    
    // 아이디 저장 로직
    if (isSaveId) {
      storage(localStorage).setItem('saveId', email);
    } else {
      storage(localStorage).removeItem('saveId');
    }
    
    // 로그인 로직
    const userData = { 'email': email, password };
    try {
      const { headers, data } = await login(userData);
      if (!data.login) {
        alert('아이디나 비밀번호를 다시 확인해주세요.');
        return
      }
      data.userInfo['token'] = headers['access-token']
      alert(`${data.userInfo.name}님 환영합니다!`);
      onLogin(data.userInfo);
      onChangeLoginStatus(true);
      history.push('/main');
    } catch {
      alert('예기치 못한 에러가 발생했습니다. 관리자에게 문의해주세요.');
      return
    }
  };

  return (
    <div className="login-form">
      <h2>로그인</h2>
      <form onSubmit={onSubmit}>
        <article className="input-email">
          <label htmlFor="email" name="email">아이디</label>
          <input type="email" id="email" value={email} onChange={onChange} />
        </article>
        <article className="input-password">
          <label htmlFor="password" name="password">비밀번호</label>
          <input type="password" id="password" value={password} onChange={onChange} />
        </article>
        <article className="save-id">
          <label htmlFor="save-id" name="save-id">아이디 저장</label>
          <input ref={checkBoxEl} type="checkbox" id="save-id" value={isSaveId} onChange={onChangeIsSaveId}/>
        </article>
        <button type="submit" className="btn btn--small login-form-button"><i className="fas fa-sign-in-alt" /> 로그인</button>
        <Link
          to="login/findpassword"
          className="btn btn--small login-form-button"
        >
          <i className="fas fa-key" /> 비밀번호 찾기
        </Link>
      </form>
    </div>
  );
});

export default withRouter(Login);