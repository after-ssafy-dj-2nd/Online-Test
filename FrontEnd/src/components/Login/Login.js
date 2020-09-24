import React, { useReducer, useState, useEffect, useRef, useCallback, memo } from 'react';
import { withRouter, Link } from 'react-router-dom';
import { storage } from '../../util/storage';
import { reducer } from '../../util/reducer';
import { login } from '../../api/modules/user';
import Modal from '../Common/Modal';
import './Login.css';

const Login = memo(({ history }) => {
  const [isSaveId, setIsSaveId] = useState(false);
  const checkBoxEl = useRef(null);

  const [isShowModal, setIsShowModal] = useState(false);

  const showModal = useCallback(() => {
    setIsShowModal(true);
  }, []);
  
  const closeModal = useCallback(() => {
    setIsShowModal(false);
  }, []);

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
  }, [checkBoxEl]);
  
  const onChange = e => {
    dispatch(e.target);
  };
  
  const onChangeIsSaveId = useCallback(e => {
    setIsSaveId(e.target.checked);
  }, []);

  const onSubmit = async e => {
    e.preventDefault();

    // 아이디, 비밀번호 빈 값인지 확인하는 로직
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
    
    // 로그인 로직
    const userData = { 'user_id': id, password };
    try {
      const { data } = await login(userData);
      if (!data.login) {
        alert('아이디나 비밀번호를 다시 확인해주세요.');
        return
      }
      alert(`${data.userInfo.name}님 환영합니다!`);
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
        <button type="submit" className="btn btn--small login-form-button"><i className="fas fa-sign-in-alt" /> 로그인</button>
        <Link
          to="login/findpassword"
          className="btn btn--small login-form-button"
        >
          <i className="fas fa-key" /> 비밀번호 찾기
        </Link>
      </form>

      {/* 모달 테스트(추후 삭제할 예정) */}
      <button onClick={showModal} className="btn btn--small">모달 열기(테스트용)</button>
      <Modal isShow={isShowModal} close={closeModal} title="Modal Title">
        <div>
          <p>컨텐츠1</p>
          <p>컨텐츠2</p>
          <img src="https://via.placeholder.com/150" alt="임시 이미지" />
        </div>
      </Modal>
    </div>
  );
});

export default withRouter(Login);