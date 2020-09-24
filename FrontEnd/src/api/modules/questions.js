import instance from '../index';

// question fetch API
export function fetchQuestions() {
  return instance.get('questions');
}