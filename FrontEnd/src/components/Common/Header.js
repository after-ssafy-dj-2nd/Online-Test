import React, { useState, useCallback, useEffect } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { saveUserInfo, removeUserInfo, changeLoginStatus } from '../../store/users';
import { useSelector, useDispatch } from 'react-redux';
import './Header.css';
import Modal from './Modal';
import { storage } from '../../util/storage';

const Header = ({ history }) => {
  const { loginStatus, userInfo } = useSelector(state => state.users);
  const [isShowModal, setIsShowModal] = useState(false);
  const [userName, setUserName] = useState(null);

  const showModal = useCallback(() => {
    setIsShowModal(true);
  }, []);
  
  const closeModal = useCallback(() => {
    setIsShowModal(false);
  }, []);

  const userDispatch = useDispatch();
  const onLogin = useCallback(userInfo => userDispatch(saveUserInfo(userInfo)), [userDispatch]);
  const onLogout = useCallback(() => userDispatch(removeUserInfo()), [userDispatch]);
  const onChangeLoginStatus = useCallback(loginStatus => userDispatch(changeLoginStatus(loginStatus)), [userDispatch]);

  // 새로고침할 때만 동작
  useEffect(() => {
    const refreshLoginStatus = storage(sessionStorage).getItem('loginStatus');
    const refreshLoginInfo = storage(sessionStorage).getItem('userInfo');
    if (refreshLoginInfo && refreshLoginStatus) {
      onChangeLoginStatus(refreshLoginStatus);
      onLogin(refreshLoginInfo);
    }
  }, [onLogin, onChangeLoginStatus]);

  // 새로고침시 로그인 상태 유지하기 위해 sessionStorage에 정보 저장
  useEffect(() => {
    if (loginStatus) {
      storage(sessionStorage).setItem('loginStatus', loginStatus);
    }
  }, [loginStatus]);

  useEffect(() => {
    if (userInfo) {
      storage(sessionStorage).setItem('userInfo', userInfo);
      setUserName(userInfo.name);
    }
  }, [userInfo]);

  // 로그아웃 로직
  const logout = () => {
    onLogout();
    onChangeLoginStatus(false);
    alert('로그아웃 되었습니다.');
    storage(sessionStorage).removeItem('loginStatus');
    storage(sessionStorage).removeItem('userInfo');
    history.push('/');
  };

  return (
    <>
      <header>
        <div>
          <Link to="/">메인</Link>
          {!loginStatus
            ? (
              <>
                <Link to="/login">로그인</Link>
                <Link to="/signup">회원가입</Link>
              </>
              )
            : <span onClick={showModal}>로그아웃</span>
          }
          {loginStatus && userInfo.auth === 1 && <Link to="/teacher">선생님</Link>}
        </div>
        <div>
          {userName && <span>Welcome, {userName}</span>}
        </div>
      </header>
      <Modal isShow={isShowModal} close={closeModal} title="Logout">
        <div className="logout-modal">
          <i className="fas fa-sign-out-alt" />
          <p>로그아웃 하시겠습니까?</p>
          <button className="btn logout-button" onClick={logout}>Logout</button>
        </div>
      </Modal>
    </>
  );
};

export default withRouter(Header);