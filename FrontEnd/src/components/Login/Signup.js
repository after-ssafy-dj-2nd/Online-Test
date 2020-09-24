import React, { memo, useReducer, useMemo } from 'react';
import { withRouter } from 'react-router-dom';
import validation from '../../util/validation';
import { reducer } from '../../util/reducer';
import { signup } from '../../api/modules/user';
import './Signup.css';

const signupTitle = signupType => `${signupType === 'student' ? '학생용' : '선생님용'} 계정 회원가입`;

const Signup = memo(({ signupType, history }) => {
  const signupFormTitle = useMemo(() => signupTitle(signupType), [signupType]);
  const [state, dispatch] = useReducer(reducer, {
    id: '',
    password: '',
    username: '',
    email: ''
  });

  const { id, password, username, email } = state;

  const onChange = e => {
    dispatch(e.target);
  }

  const onSubmit = async e => {
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
      alert('2자 이상 5자 이하의 한글 이름을 다시 작성해주세요.');
      return
    }
    if (!validation.emailValidation(email)) {
      alert('이메일 양식을 지켜서 다시 작성해주세요.');
      return
    }

    // 회원가입 로직
    const userData = {
      'auth': signupType === 'student' ? 0 : 1,
      'user_id': id,
      'name': username,
      password,
      email,
    };
    try {
      const response = await signup(userData);
      console.log(response)
      if (response.status === 400) {
        alert('중복된 아이디나 이메일 주소가 있습니다.\n다른 아이디나 이메일 주소로 작성해주세요.');
        return
      }
      if (response.status === 200) {
        alert(`성공적으로 회원가입되었습니다!\n${username}님 환영합니다!`);
        history.push('/main');
      }
    } catch {
      alert('예기치 못한 에러가 발생했습니다. 관리자에게 문의해주세요.');
      return
    }
  }

  return (
    <div className="signup-form">
      <h2>{ signupFormTitle }</h2>
      <form onSubmit={onSubmit}>
        <article className="input-id">
          <label htmlFor="id" name="id">아이디</label>
          <input type="text" id="id" value={id} onChange={onChange} />
        </article>
        <small>(아이디는 영어 소문자 4자 이상, 숫자 2자 이상으로 만들어주세요.)</small>
        <article className="input-password">
          <label htmlFor="password" name="password">비밀번호</label>
          <input type="password" id="password" value={password} onChange={onChange} />
        </article>
        <small>(비밀번호는 영어 소문자 6자 이상, 숫자 4자 이상으로 만들어주세요.)</small>
        <article className="input-username">
          <label htmlFor="username" name="username">이름</label>
          <input type="text" id="username" value={username} onChange={onChange} />
        </article>
        <small>
          (가입하시는 분의 한글 이름(2자 이상 5자 이하)을 작성해주세요.)<br />
          (5자 이상인 경우에는 앞 5글자만 작성해주세요.)
        </small>
        <article className="input-email">
          <label htmlFor="email" name="email">이메일</label>
          <input type="email" id="email" value={email} onChange={onChange} />
        </article>
        <small>(이메일 양식을 지켜서 작성해주세요.)</small>
        <button type="submit" className="btn btn--small signup-button"><i className="fas fa-user-plus" /> 회원가입</button>
      </form>
    </div>
  );
});

export default withRouter(Signup);