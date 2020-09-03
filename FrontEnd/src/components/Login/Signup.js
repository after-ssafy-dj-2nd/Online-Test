import React, { useState, useCallback, memo } from 'react';
import { withRouter } from 'react-router-dom';
import validation from '../../util/validation';
import './Signup.css';

const signupFormTitle = signupType => `${signupType === 'student' ? '학생용' : '선생님용'} 계정 회원가입`;

const Signup = memo(({ signupType, history }) => {
  const [id, setId] = useState('');
  const [password, setPassword] = useState('');
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');

  const onChangeId = useCallback(e => {
    setId(e.target.value);
  }, []);

  const onChangePassword = useCallback(e => {
    setPassword(e.target.value);
  }, []);

  const onChangeUsername = useCallback(e => {
    setUsername(e.target.value);
  }, []);

  const onChangeEmail = useCallback(e => {
    setEmail(e.target.value);
  }, []);

  const onSubmitSignup = e => {
    e.preventDefault();
    if (id.length === 0 && password.length === 0 && username.length === 0 && email.length === 0) {
      alert('회원가입 양식을 모두 작성해주세요.');
      return
    }
    if (!validation.idValidation(id)) {
      alert('최소 조건에 맞게 아이디를 다시 작성해주세요.');
      return
    }
    if (!validation.passwordValidation(password)) {
      alert('최소 조건에 맞게 비밀번호를 다시 작성해주세요.');
      return
    }
    if (!validation.usernameValidation(username)) {
      alert('한글 이름을 다시 작성해주세요.');
      return
    }
    if (!validation.emailValidation(email)) {
      alert('이메일 양식을 지켜서 다시 작성해주세요.');
      return
    }
    history.push('/main');
  }

  return (
    <div className="signup-form">
      <h2>{ signupFormTitle(signupType) }</h2>
      <form onSubmit={onSubmitSignup} >
        <article className="input-id">
          <label htmlFor="id" name="id">아이디</label>
          <input type="text" id="id" value={id} onChange={onChangeId} />
        </article>
        <small>(아이디는 영어 소문자 4자 이상, 숫자 2자 이상으로 만들어주세요.)</small>
        <article className="input-password">
          <label htmlFor="password" name="password">비밀번호</label>
          <input type="password" id="password" value={password} onChange={onChangePassword} />
        </article>
        <small>(비밀번호는 영어 소문자 6자 이상, 숫자 4자 이상으로 만들어주세요.)</small>
        <article className="input-username">
          <label htmlFor="username" name="username">이름</label>
          <input type="text" id="username" value={username} onChange={onChangeUsername} />
        </article>
        <small>(가입하시는 분의 한글 이름을 작성해주세요.)</small>
        <article className="input-email">
          <label htmlFor="email" name="email">이메일</label>
          <input type="email" id="email" value={email} onChange={onChangeEmail} />
        </article>
        <small>(이메일 양식을 지켜서 작성해주세요.)</small>
        <button type="submit">회원가입</button>
      </form>
    </div>
  );
});

export default withRouter(Signup);