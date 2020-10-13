import { createAction, handleActions } from 'redux-actions';

const TOGGLE_AUTH = 'tryout/TOGGLE_AUTH';
const SAVE_AUTH_INFO = 'tryout/SAVE_AUTH_INFO';
const REMOVE_AUTH_INFO = 'tryout/REMOVE_AUTH_INFO';

export const toggleAuth = createAction(TOGGLE_AUTH);
export const saveAuthInfo = createAction(SAVE_AUTH_INFO);
export const removeAuthInfo = createAction(REMOVE_AUTH_INFO);

const initialState = {
  auth: false, // 해당 시험 볼 수 있는지 인증 체크 변수
  examInfo: null, // 시험 정보
  studentInfo: null // 시험 응시자(학생) 정보
}

const tryoutInfo = handleActions(
  {
    [TOGGLE_AUTH]: (state) => ({
      ...state,
      auth: true
    }),
    [SAVE_AUTH_INFO]: (state, { payload }) => ({
      ...state,
      examInfo: payload.examInfo,
      studentInfo: payload.studentInfo
    }),
    [REMOVE_AUTH_INFO]: () => ({
      auth: false,
      examInfo: null,
      studentInfo: null
    })
  },
  initialState,
)

export default tryoutInfo;