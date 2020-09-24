import React, { memo, useReducer } from 'react';
import { withRouter } from 'react-router-dom';
import { reducer } from '../../util/reducer';
import './FindPassword.css';

const FindPassword = memo(({ history }) => {
  const [state, dispatch] = useReducer(reducer, {
    id: '',
    email: ''
  });

  const { id, email } = state;

  const onChange = e => {
    dispatch(e.target);
  };

  const onSubmit = e => {
    e.preventDefault();
    if (id.length === 0) {
      alert('아이디를 입력해주세요.');
      return
    }
    if (email.length === 0) {
      alert('이메일을 입력해주세요.');
      return
    }

    // 추후 state 값을 백엔드로 보내서 입력한 이메일로 임시 비밀번호 보내는 로직 구현

    alert(`${email} 으로 임시 비밀번호를 보냈습니다.\n임시 비밀번호로 다시 로그인해주세요.`);
    history.push('/login');
  };

  return (
    <div className="find-password-form">
      <h2>비밀번호 찾기</h2>
      <form onSubmit={onSubmit}>
        <article className="input-id">
          <label htmlFor="id" name="id">아이디</label>
          <input type="text" id="id" value={id} onChange={onChange} />
        </article>
        <article className="input-email">
          <label htmlFor="email" name="email">이메일</label>
          <input type="email" id="email" value={email} onChange={onChange} />
        </article>
        <small className="email-message">(회원가입시 작성했던 이메일을 입력해주세요.)</small>
        <article className="button-wrapper">
          <div className="temp-password-alert">
            <i className="fas fa-exclamation-circle" />
            <p>
              작성하신 이메일 주소로 임시 비밀번호를 보내드립니다.<br/>
              로그인 후 회원정보 수정에서 비밀번호를 꼭 변경해주세요!
            </p>
          </div>
          <button type="submit" className="btn btn--small find-password-button"><i className="fas fa-key" /> 비밀번호 찾기</button>
        </article>
      </form>
    </div>
  );
});

export default withRouter(FindPassword);