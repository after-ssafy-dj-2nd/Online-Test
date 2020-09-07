import React, { useReducer, useState, useEffect, useRef, useCallback } from 'react';
import { withRouter, Link } from 'react-router-dom';
import { storage } from '../../util/storage';
import './Login.css';

const reducer = (state, action) => {
  return {
    ...state,
    [action.id]: action.value
  };
}

const Login = ({ history }) => {
  const [isSaveId, setIsSaveId] = useState(false);
  const checkBoxEl = useRef(null);

  const [state, dispatch] = useReducer(reducer, {
    id: '',
    password: ''
  });

  const { id, password } = state;

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
  }, []);
  
  const onChange = useCallback(e => {
    dispatch(e.target);
  }, []);
  
  const onChangeIsSaveId = useCallback(e => {
    setIsSaveId(e.target.checked);
  }, []);

  const onSubmit = e => {
    e.preventDefault();
    if (id.length === 0) {
      alert('아이디를 입력해주세요.');
      return
    }
    if (password.length === 0) {
      alert('비밀번호를 입력해주세요.');
      return
    }

    // 아이디 저장 로직
    if (isSaveId) {
      storage(localStorage).setItem('saveId', id);
    } else {
      storage(localStorage).removeItem('saveId');
    }

    // 추후 이 부분에 state 값을 백엔드로 보내는 로그인 로직 작성

    history.push('/main');
  };

  return (
    <div className="login-form">
      <h2>로그인</h2>
      <form onSubmit={onSubmit}>
        <article className="input-id">
          <label htmlFor="id" name="id">아이디</label>
          <input type="text" id="id" value={id} onChange={onChange} />
        </article>
        <article className="input-password">
          <label htmlFor="password" name="password">비밀번호</label>
          <input type="password" id="password" value={password} onChange={onChange} />
        </article>
        <article className="save-id">
          <label htmlFor="save-id" name="save-id">아이디 저장</label>
          <input ref={checkBoxEl} type="checkbox" id="save-id" value={isSaveId} onChange={onChangeIsSaveId}/>
        </article>
        <button type="submit"><i className="fas fa-sign-in-alt" /> 로그인</button>
        <Link
          to="login/findpassword"
          className="find-password-link"
        >
          <i className="fas fa-key" /> 비밀번호 찾기
        </Link>
      </form>
    </div>
  );
};

export default withRouter(Login);