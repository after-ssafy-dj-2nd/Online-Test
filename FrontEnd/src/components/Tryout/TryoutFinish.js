import React, { useCallback } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
import { removeAuthInfo } from '../../store/tryout';
import './TryoutFinish.css';

const TryoutFinish = () => {
  const { auth } = useSelector(state => state.tryout);

  const authDispatch = useDispatch();
  const onRemoveAuth = useCallback(() => authDispatch(removeAuthInfo()), [authDispatch]);

  if (auth) {
    onRemoveAuth();
  }

  return (
    <div className="finish-box">
      <i className="fas fa-clock"></i>
      <p>시험이 종료되었습니다.</p>
      <p>수고하셨습니다.</p>
      <Link className="btn btn--xsmall main-btn" to="/">메인 페이지로 이동</Link>
    </div>
  );
};

export default TryoutFinish;