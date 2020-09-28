import { createAction, handleActions } from 'redux-actions';

const LOGIN = 'users/LOGIN';
const LOGOUT = 'users/LOGOUT';
const CHANGE_LOGIN_STATUS = 'users/CHANGE_LOGIN_STATUS';

export const saveUserInfo = createAction(LOGIN, userInfo => userInfo);
export const removeUserInfo = createAction(LOGOUT);
export const changeLoginStatus = createAction(CHANGE_LOGIN_STATUS, loginStatus => loginStatus);

const initialState = {
  loginStatus: false,
  userInfo: null
};

const userInfo = handleActions(
  {
    [LOGIN]: (state, { payload: userInfo }) => ({
      ...state,
      userInfo
    }),
    [LOGOUT]: (state) => ({
      ...state,
      userInfo: null
    }),
    [CHANGE_LOGIN_STATUS]: (state, { payload: loginStatus }) => ({
      ...state,
      loginStatus
    })
  },
  initialState,
)

export default userInfo;