import instance from '../index';

// signup API
export function signup(userData) {
  return instance.post('signup', userData);
}

// login API
export function login(userData) {
  return instance.post('login', userData);
}