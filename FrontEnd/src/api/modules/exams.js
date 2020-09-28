import instance from '../index';

// exam fetch API
export function fetchExams() {
  return instance.get('exams')
}