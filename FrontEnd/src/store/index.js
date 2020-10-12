import { combineReducers } from 'redux';
import users from './users';
import tryout from './tryout';

const rootReducer = combineReducers({
  users,
  tryout
});

export default rootReducer;