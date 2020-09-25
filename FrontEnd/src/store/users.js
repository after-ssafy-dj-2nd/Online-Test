import { createAction, handleActions } from 'redux-actions';

const LOGIN = 'users/LOGIN';
const LOGOUT = 'users/LOGOUT';
const TOGGLE_LOGIN_STATUS = 'users/TOGGLE_LOGIN_STATUS';

export const saveUserInfo = createAction(LOGIN, userInfo => userInfo);
export const removeUserInfo = createAction(LOGOUT);
export const toggleLoginStatus = createAction(TOGGLE_LOGIN_STATUS);

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
    [TOGGLE_LOGIN_STATUS]: (state) => ({
      ...state,
      loginStatus: !state.loginStatus
    })
  },
  initialState,
)

export default userInfo;