import React, { useCallback, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { withRouter } from 'react-router';
import { toggleAuth, saveAuthInfo } from '../../store/tryout';
import './Authentication.css';

// 인증 처리 과정 페이지
const Authentication = ({ location, history }) => {
  const nextUrl = `${location.pathname}/wait/${location.search}`; // 인증 성공 후 이동할 대기화면 페이지 url 주소
  const query = new URLSearchParams(location.search);
  const token = query.get('token');

  const authDispatch = useDispatch();
  const onToggleAuth = useCallback(authStatus => authDispatch(toggleAuth(authStatus)), [authDispatch]);
  const onSaveAuth = useCallback(authInfo => authDispatch(saveAuthInfo(authInfo)), [authDispatch]);

  useEffect(() => {
    if (!token) { // url에 token query parameter가 없는 경우 메인 페이지로 redirect
      history.push('/');
      return
    }

    // 이 부분에 token 값을 백엔드로 보내고 결과 값을 받는 로직 작성

    // 1) 백엔드로 token 값 보내서 인증 결과 받아온 후 인증되면
    // 시험 정보와 응시자 정보 redux에 저장 후 시험 대기 화면으로 이동
    // onToggleAuth(true);
    // history.push(nextUrl);

    // 2) 만약 인증되지 않은 경우 '인증되지 않았다'는 문구를 alert()로 띄우고 '확인' 누르면 메인 페이지로 자동 이동
    
    // 3) 만약 시험이 종료된 경우 시험 종료 페이지로 바로 이동 (ex. history.push('/tryout/123/fisnish'))
  }, [token, history]);

  return (
    <div className="test-authentication">
      <i className="fas fa-spinner"></i>
      <p>인증 확인중입니다.</p>
      <p>조금만 기다려주세요.</p>
    </div>
  );
};

export default withRouter(Authentication);